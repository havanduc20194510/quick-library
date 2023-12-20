package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.BorrowBookInstanceDto;

import java.util.List;

public interface BorrowBookInstanceService {

    String create(Long userId, Long bookId, Long libraryId);

    //List<BorrowBookInstanceDto> getBorrowBookInstancesByBorrowRequestId(Long borrowRequestId);



}
