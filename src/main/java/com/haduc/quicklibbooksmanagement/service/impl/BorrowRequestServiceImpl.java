package com.haduc.quicklibbooksmanagement.service.impl;

import com.haduc.quicklibbooksmanagement.dto.BorrowRequestDto;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestInfo;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestItem;
import com.haduc.quicklibbooksmanagement.entity.*;
import com.haduc.quicklibbooksmanagement.mapper.BorrowRequestMapper;
import com.haduc.quicklibbooksmanagement.mapper.LibraryMapper;
import com.haduc.quicklibbooksmanagement.mapper.UserMapper;
import com.haduc.quicklibbooksmanagement.repository.*;
import com.haduc.quicklibbooksmanagement.service.BorrowRequestService;
import com.haduc.quicklibbooksmanagement.util.RandomCodeGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BorrowRequestServiceImpl implements BorrowRequestService {
    BorrowRequestRepository borrowRequestRepository;
    BorrowRequestMapper borrowRequestMapper;
    LibraryRepository libraryRepository;
    UserRepository userRepository;

    LibraryMapper libraryMapper;

    UserMapper userMapper;

    BorrowBookInstanceRepository borrowBookInstanceRepository;
    LibraryBookRepository libraryBookRepository;

    @Override
    public String createBorrowRequest(BorrowRequestDto borrowRequestDto, Long userId, Long libraryId) {
        List<BorrowRequest> borrowRequestList = borrowRequestRepository.findByUserIdAndLibraryId(userId, libraryId);
        boolean hasBorrowRequest = false;
        if(borrowRequestList != null && borrowRequestList.size() > 0) {
            for(BorrowRequest borrowRequest1 : borrowRequestList) {
                if(borrowRequest1.getLibrary().getId().equals(libraryId) && !borrowRequest1.getStatus().equals(BorrowStatus.RETURNED)) {
                    hasBorrowRequest = true;
                    break;
                }
            }
        }
        if(!hasBorrowRequest) {
            Date currentDate = new Date();
            // Tạo một instance của Calendar
            Calendar calendar = Calendar.getInstance();
            // Đặt thời gian của calendar là ngày hiện tại
            calendar.setTime(currentDate);
            // Thêm 2 ngày vào calendar
            calendar.add(Calendar.DATE, 2);
            // Lấy ra ngày sau khi thêm 2 ngày
            Date requestDueDate = calendar.getTime();

            Library library = libraryRepository.findById(libraryId).orElse(null);
            User user = userRepository.findById(userId).orElse(null);
            borrowRequestDto.setLibrary(libraryMapper.toLibraryDto(library));
            borrowRequestDto.setUser(userMapper.toUserDto(user));
            borrowRequestDto.setStatus(BorrowStatus.UNSENT);
            borrowRequestDto.setRequestDueDate(requestDueDate);
            borrowRequestDto.setCreatedAt(currentDate);
            borrowRequestDto.setUpdatedAt(currentDate);
            BorrowRequest borrowRequest = borrowRequestMapper.toBorrowRequest(borrowRequestDto);
            borrowRequestRepository.save(borrowRequest);
            return "Borrow request created successfully";
        }
        return "You have a borrow request in this library";
    }

    @Override
    public boolean returnBorrowRequest(String code, Long libraryId) {
        BorrowRequest borrowRequest = borrowRequestRepository.findByCode(code);
        Date currentDate = new Date();
        if(borrowRequest != null) {
            if(borrowRequest.getStatus().equals(BorrowStatus.BORROWING)) {
                borrowRequest.setStatus(BorrowStatus.RETURNED);
                borrowRequest.setReturnDate(currentDate);
                borrowRequestRepository.save(borrowRequest);
                List<BorrowBookInstance> borrowBookInstances = borrowBookInstanceRepository.findByBorrowRequest_Id(borrowRequest.getId());
                for(BorrowBookInstance borrowBookInstance : borrowBookInstances) {
                    LibraryBook libraryBook = borrowBookInstance.getLibraryBook();
                    int quantity = libraryBook.getQuantity() + 1;
                    libraryBook.setQuantity(quantity);
                    libraryBookRepository.save(libraryBook);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<BorrowRequestItem> getAllBorrowRequestItems(Long libraryId) {
        Date currentDate = new Date();
        List<BorrowRequestItem> borrowRequestItems = borrowRequestRepository.findAllBorrowRequestItems(libraryId);
        if(borrowRequestItems != null) {
            for (BorrowRequestItem borrowRequestItem : borrowRequestItems) {
                if (borrowRequestItem.getReturnDate() != null & currentDate.after(borrowRequestItem.getRequestDueDate()) && borrowRequestItem.getStatus().equals(BorrowStatus.BORROWING)) {
                    BorrowRequest borrowRequest = borrowRequestRepository.findById(borrowRequestItem.getId()).orElse(null);
                    borrowRequest.setStatus(BorrowStatus.OVERDUE);
                    borrowRequestRepository.save(borrowRequest);
                }
            }
        }
        List<BorrowRequestItem> borrowRequestItems1 = borrowRequestRepository.findAllBorrowRequestItems(libraryId);
        return borrowRequestItems1;
    }

    @Override
    public List<BorrowRequestInfo> getBorrowRequestsByUserId(Long userId) {
        List<BorrowRequestInfo> borrowRequestInfos = borrowRequestRepository.findByUserIdAndCount(userId);
        if(borrowRequestInfos != null) {
            for (BorrowRequestInfo borrowRequestInfo : borrowRequestInfos) {
                if (borrowRequestInfo.getStatus().equals(BorrowStatus.BORROWING)) {
                    Date currentDate = new Date();
                    if (borrowRequestInfo.getReturnDate() != null && currentDate.after(borrowRequestInfo.getRequestDueDate())) {
                        BorrowRequest borrowRequest = borrowRequestRepository.findById(borrowRequestInfo.getId()).orElse(null);
                        borrowRequest.setStatus(BorrowStatus.OVERDUE);
                        borrowRequestRepository.save(borrowRequest);
                    }
                }
            }
        }
        List<BorrowRequestInfo> borrowRequestInfos1 = borrowRequestRepository.findByUserIdAndCount(userId);
        return borrowRequestInfos1;
    }

    @Override
    public String sentBorrowRequest(Long borrowRequestId, Date borrowDate, Date requestDueDate) {
        BorrowRequest borrowRequest = borrowRequestRepository.findById(borrowRequestId).orElse(null);
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();
        String code = randomCodeGenerator.generateRandomCode();
        if(borrowRequest.getStatus().equals(BorrowStatus.UNSENT)) {
            borrowRequest.setStatus(BorrowStatus.REQUESTED);
            borrowRequest.setBorrowDate(borrowDate);
            borrowRequest.setRequestDueDate(requestDueDate);
            borrowRequest.setCode(code);
            borrowRequestRepository.save(borrowRequest);
            List<BorrowBookInstance> borrowBookInstances = borrowBookInstanceRepository.findByBorrowRequest_Id(borrowRequestId);
            for(BorrowBookInstance borrowBookInstance : borrowBookInstances) {
                LibraryBook libraryBook = borrowBookInstance.getLibraryBook();
                int quantity = libraryBook.getQuantity() - 1;
                libraryBook.setQuantity(quantity);
                libraryBookRepository.save(libraryBook);
            }
            return "Sent borrow request successfully" + "-" + "code is: " + borrowRequest.getCode();
        }
        return "Borrow request has been sent" + "-" + "code is: " + borrowRequest.getCode();
    }

    @Override
    public String deleteBorrowRequest(Long borrowRequestId) {
        BorrowRequest borrowRequestDelete = borrowRequestRepository.findById(borrowRequestId).get();
        if(borrowRequestDelete.getStatus().equals(BorrowStatus.UNSENT) || borrowRequestDelete.getStatus().equals(BorrowStatus.REQUESTED)){
            List<BorrowBookInstance> borrowBookInstances = borrowBookInstanceRepository.findByBorrowRequest_Id(borrowRequestId);
            for(BorrowBookInstance borrowBookInstance : borrowBookInstances) {
                LibraryBook libraryBook = borrowBookInstance.getLibraryBook();
                int quantity = libraryBook.getQuantity() + 1;
                libraryBook.setQuantity(quantity);
                libraryBookRepository.save(libraryBook);
            }
            borrowRequestRepository.deleteById(borrowRequestId);
            return "Delete borrow request successfully";
        }
        return "Delete borrow request failed!. Borrow request has been accepted";
    }
    @Override
    public int acceptBorrowRequest(String code, Long libraryId) {
        BorrowRequest borrowRequest = borrowRequestRepository.findByCode(code);
        if(borrowRequest == null) return -1;
        else if (!borrowRequest.getLibrary().getId().equals(libraryId)) return -1;
        else{
            if(borrowRequest.getStatus().equals(BorrowStatus.REQUESTED)) {
                borrowRequest.setStatus(BorrowStatus.BORROWING);
                borrowRequestRepository.save(borrowRequest);
                return 1;
            }
            return 0;
        }
    }
}
