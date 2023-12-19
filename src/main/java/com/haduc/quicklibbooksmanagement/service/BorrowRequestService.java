package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.BorrowRequestDto;
import com.haduc.quicklibbooksmanagement.entity.BorrowRequest;

import java.util.List;

public interface BorrowRequestService {

    BorrowRequest createBorrowRequest(BorrowRequestDto borrowRequest);
    BorrowRequest updateBorrowRequest(BorrowRequestDto borrowRequest);
    List<BorrowRequest> getAllBorrowRequests();
}
