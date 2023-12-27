package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.BorrowBookInfor;
import com.haduc.quicklibbooksmanagement.dto.BorrowBookInstanceDto;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestBookInfo;

import java.util.List;

public interface BorrowBookInstanceService {

    String create(Long userId, Long bookId, Long libraryId);

    List<BorrowBookInfor> getBorrowBookInstancesByBorrowRequestId(Long borrowRequestId);

    List<BorrowBookInfor> getBorrowBookInstancesByUserId(Long userId);
    String deleteBookInRequest(Long borrowBookInstanceId);
}
