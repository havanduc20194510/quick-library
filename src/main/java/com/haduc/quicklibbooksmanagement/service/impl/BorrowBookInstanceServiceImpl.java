package com.haduc.quicklibbooksmanagement.service.impl;

import com.haduc.quicklibbooksmanagement.dto.BorrowBookInfor;
import com.haduc.quicklibbooksmanagement.dto.BorrowBookInstanceDto;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestBookInfo;
import com.haduc.quicklibbooksmanagement.entity.*;
import com.haduc.quicklibbooksmanagement.mapper.BorrowBookInstanceMapper;
import com.haduc.quicklibbooksmanagement.repository.*;
import com.haduc.quicklibbooksmanagement.service.BorrowBookInstanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class BorrowBookInstanceServiceImpl implements BorrowBookInstanceService {
    BorrowRequestRepository bookRequestRepository;

    BookRepository bookRepository;
    private LibraryBookRepository libraryBookRepository;
    private BorrowBookInstanceRepository borrowBookInstanceRepository;

    BorrowBookInstanceMapper borrowBookInstanceMapper;

    BorrowRequestRepository borrowRequestRepository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;


    @Override
    public String create(Long userId, Long bookId, Long libraryId) {
        BorrowBookInstanceDto borrowBookInstanceDto = new BorrowBookInstanceDto();
        LibraryBook libraryBook = libraryBookRepository.findByBookIdAndLibrary_Id(bookId,libraryId);
        Library library = libraryBook.getLibrary();
        Date currentDate = new Date();
        BorrowRequest borrowRequest = new BorrowRequest();
        if(libraryBook.getQuantity() <= 0){
            return "book out of stock";
        }
        List<BorrowRequest> borrowRequests = bookRequestRepository.findByUserIdAndLibraryId(userId, library.getId());
        if (borrowRequests.size() > 0) {
            for (BorrowRequest request : borrowRequests) {
                if (request.getStatus().equals(BorrowStatus.UNSENT)) {
                    borrowBookInstanceDto.setBorrowRequest(request);
                    borrowBookInstanceDto.setLibraryBook(libraryBook);
                    borrowBookInstanceDto.setCreatedAt(currentDate);
                    borrowBookInstanceDto.setUpdatedAt(currentDate);
                    if(borrowBookInstanceRepository.findByBorrowRequest_IdAndLibraryBook_Id(request.getId(),libraryBook.getId()) != null){
                        return "book already in request";
                    }
                    borrowBookInstanceRepository.save(borrowBookInstanceMapper.toBorrowBookInstance(borrowBookInstanceDto));
                    break;
                }else if(!request.getStatus().equals(BorrowStatus.RETURNED)) {
                    return "book already in requested list. Cannot add more book";
                }else {
                    User user = userRepository.findById(userId).orElseThrow();
                    borrowRequest.setUser(user);
                    borrowRequest.setLibrary(library);
                    borrowRequest.setStatus(BorrowStatus.UNSENT);
                    borrowRequest.setCreatedAt(currentDate);
                    borrowRequest.setUpdatedAt(currentDate);
                    borrowRequestRepository.save(borrowRequest);
                    borrowBookInstanceDto.setBorrowRequest(borrowRequest);
                    borrowBookInstanceDto.setLibraryBook(libraryBook);
                    borrowBookInstanceDto.setCreatedAt(currentDate);
                    borrowBookInstanceDto.setUpdatedAt(currentDate);
                    if(borrowBookInstanceRepository.findByBorrowRequest_IdAndLibraryBook_Id(borrowRequest.getId(),libraryBook.getId()) != null){
                        return "book already in request";
                    }
                    borrowBookInstanceRepository.save(borrowBookInstanceMapper.toBorrowBookInstance(borrowBookInstanceDto));
                }
            }
        }else {
            User user = userRepository.findById(userId).orElseThrow();
            borrowRequest.setUser(user);
            borrowRequest.setLibrary(library);
            borrowRequest.setStatus(BorrowStatus.UNSENT);
            borrowRequest.setCreatedAt(currentDate);
            borrowRequest.setUpdatedAt(currentDate);
            borrowRequestRepository.save(borrowRequest);
            borrowBookInstanceDto.setBorrowRequest(borrowRequest);
            borrowBookInstanceDto.setLibraryBook(libraryBook);
            borrowBookInstanceDto.setCreatedAt(currentDate);
            borrowBookInstanceDto.setUpdatedAt(currentDate);
            if(borrowBookInstanceRepository.findByBorrowRequest_IdAndLibraryBook_Id(borrowRequest.getId(),libraryBook.getId()) != null){
                return "book already in request";
            }
            borrowBookInstanceRepository.save(borrowBookInstanceMapper.toBorrowBookInstance(borrowBookInstanceDto));
        }
        return "add book to request successfully";
    }

    @Override
    public List<BorrowBookInfor> getBorrowBookInstancesByBorrowRequestId(Long borrowRequestId) {
        List<BorrowRequestBookInfo> borrowRequestBookInfoList = borrowBookInstanceRepository.findBooksByBorrowRequestId(borrowRequestId);
        List<BorrowBookInfor> borrowBookInforList = new ArrayList<>();
        for (BorrowRequestBookInfo borrowRequestBookInfo : borrowRequestBookInfoList) {
            BorrowBookInfor borrowBookInfor = new BorrowBookInfor();
            borrowBookInfor.setBookInfo(borrowRequestBookInfo);
            if(borrowRequestBookInfo.getParentCategoryId() != null && borrowRequestBookInfo.getParentCategoryId() > 0){
                Category category = categoryRepository.findById(borrowRequestBookInfo.getParentCategoryId()).orElseThrow();
                borrowBookInfor.setParentCategoryName(category.getName());
            }
            borrowBookInforList.add(borrowBookInfor);
        }
        return borrowBookInforList;
    }

    @Override
    public String deleteBookInRequest(Long borrowBookInstanceId) {
        BorrowBookInstance borrowBookInstance = borrowBookInstanceRepository.findById(borrowBookInstanceId).orElseThrow();
        LibraryBook libraryBook = borrowBookInstance.getLibraryBook();
        BorrowRequest borrowRequest = borrowBookInstance.getBorrowRequest();
        int newQuantity = libraryBook.getQuantity() + 1;
        if(borrowRequest.getStatus().equals(BorrowStatus.REQUESTED)){
            libraryBook.setQuantity(newQuantity);
            libraryBookRepository.save(libraryBook);
            borrowBookInstanceRepository.deleteById(borrowBookInstanceId);
            if(borrowBookInstanceRepository.findByBorrowRequest_Id(borrowRequest.getId()).size() == 0){
                borrowRequestRepository.deleteById(borrowRequest.getId());
            }
            return "delete book in request successfully";
        }else if(borrowRequest.getStatus().equals(BorrowStatus.UNSENT)){
            libraryBookRepository.save(libraryBook);
            borrowBookInstanceRepository.deleteById(borrowBookInstanceId);
            if(borrowBookInstanceRepository.findByBorrowRequest_Id(borrowRequest.getId()).size() == 0){
                borrowRequestRepository.deleteById(borrowRequest.getId());
            }
            return "delete book in request successfully";
        }else {
            return "Cannot delete book in history borrow request";
        }
    }

    @Override
    public List<BorrowBookInfor> getBorrowBookInstancesByUserId(Long userId) {
        List<BorrowRequestBookInfo> borrowRequestBookInfoList = borrowBookInstanceRepository.findBorrowBookByUserId(userId);
        List<BorrowBookInfor> borrowBookInforList = new ArrayList<>();
        for (BorrowRequestBookInfo borrowRequestBookInfo : borrowRequestBookInfoList) {
            BorrowBookInfor borrowBookInfor = new BorrowBookInfor();
            borrowBookInfor.setBookInfo(borrowRequestBookInfo);
            if(borrowRequestBookInfo.getParentCategoryId() != null && borrowRequestBookInfo.getParentCategoryId() > 0){
                Category category = categoryRepository.findById(borrowRequestBookInfo.getParentCategoryId()).orElseThrow();
                borrowBookInfor.setParentCategoryName(category.getName());
            }
            borrowBookInforList.add(borrowBookInfor);
        }
        return borrowBookInforList;
    }
}
