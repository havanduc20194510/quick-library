package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.BorrowRequestDto;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestInfo;

import java.util.List;

public interface BorrowRequestService {
    String createBorrowRequest(BorrowRequestDto borrowRequest, Long userId, Long libraryId);
    List<BorrowRequestDto> getAllBorrowRequests();

    List<BorrowRequestInfo> getBorrowRequestsByUserId(Long userId);
}
