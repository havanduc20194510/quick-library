package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.BorrowBookInstanceDto;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestBookInfo;

import java.util.List;

public interface BorrowBookInstanceService {

    String create(Long userId, Long bookId, Long libraryId);

    List<BorrowRequestBookInfo> getBorrowBookInstancesByBorrowRequestId(Long userId, Long borrowRequestId);


}
