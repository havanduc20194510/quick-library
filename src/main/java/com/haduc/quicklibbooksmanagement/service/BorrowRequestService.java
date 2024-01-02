package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.BorrowRequestDto;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestInfo;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestItem;

import java.util.Date;
import java.util.List;

public interface BorrowRequestService {
    String createBorrowRequest(BorrowRequestDto borrowRequest, Long userId, Long libraryId);
    List<BorrowRequestInfo> getBorrowRequestsByUserId(Long userId);
    String sentBorrowRequest(Long borrowRequestId, Date borrowDate, Date requestDueDate);
    int acceptBorrowRequest(String code, Long libraryId);
    String deleteBorrowRequest(Long borrowRequestId);
    boolean returnBorrowRequest(String code, Long libraryId);
    List<BorrowRequestItem> getAllBorrowRequestItems(Long libraryId);

}
