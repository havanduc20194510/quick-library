package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.BorrowBookInstanceDto;

public interface BorrowBookInstanceService {

    String create(Long userId, Long bookId, Long libraryId);



}
