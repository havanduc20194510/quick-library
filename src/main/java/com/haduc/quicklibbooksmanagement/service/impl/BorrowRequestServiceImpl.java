package com.haduc.quicklibbooksmanagement.service.impl;

import com.haduc.quicklibbooksmanagement.dto.BorrowRequestDto;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestInfo;
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
    private final LibraryRepository libraryRepository;
    private final UserRepository userRepository;

    LibraryMapper libraryMapper;

    UserMapper userMapper;

    private final BorrowBookInstanceRepository borrowBookInstanceRepository;
    private final LibraryBookRepository libraryBookRepository;

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

            Library library = libraryRepository.findById(libraryId).get();
            User user = userRepository.findById(userId).get();
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
    public List<BorrowRequestDto> getAllBorrowRequests() {
        List<BorrowRequest> borrowRequests = borrowRequestRepository.findAll();
        return borrowRequests.stream().map(borrowRequestMapper::toBorrowRequestDto).collect(Collectors.toList());
    }

    @Override
    public List<BorrowRequestInfo> getBorrowRequestsByUserId(Long userId) {
        List<BorrowRequestInfo> borrowRequestInfos = borrowRequestRepository.findByUserIdAndCount(userId);
        return borrowRequestInfos;
    }

    @Override
    public String sentBorrowRequest(Long borrowRequestId, Date borrowDate, Date requestDueDate) {
        BorrowRequest borrowRequest = borrowRequestRepository.findById(borrowRequestId).get();
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
                libraryBook.setQuantity(libraryBook.getQuantity() - 1);
                libraryBookRepository.save(libraryBook);
            }
            return "Sent borrow request successfully" + "code is: " + borrowRequest.getCode();
        }
        return "Borrow request has been sent" + "code is: " + borrowRequest.getCode();
    }

    @Override
    public String deleteBorrowRequest(Long borrowRequestId) {
        return null;
    }
}
