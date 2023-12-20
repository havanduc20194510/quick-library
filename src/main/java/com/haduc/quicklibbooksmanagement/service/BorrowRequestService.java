package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.BorrowRequestDto;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestInfo;

import java.util.Date;
import java.util.List;

public interface BorrowRequestService {
    String createBorrowRequest(BorrowRequestDto borrowRequest, Long userId, Long libraryId);
    List<BorrowRequestDto> getAllBorrowRequests();
    List<BorrowRequestInfo> getBorrowRequestsByUserId(Long userId);
    String sentBorrowRequest(Long borrowRequestId, Date borrowDate, Date requestDueDate);
    String deleteBorrowRequest(Long borrowRequestId);

}
