package com.haduc.quicklibbooksmanagement.dto;

import com.haduc.quicklibbooksmanagement.entity.BorrowStatus;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface BorrowRequestInfo {
    Long getId();
    @Value("#{target.user_id}")
    Long getUserId();
    @Value("#{target.status}")
    BorrowStatus getStatus();
    @Value("#{target.library_id}")
    Long getLibraryId();
    @Value("#{target.library_name}")
    String getLibraryName();
    @Value("#{target.borrow_date}")
    Date getBorrowDate();
    @Value("#{target.request_due_date}")
    Date getRequestDueDate();
    @Value("#{target.return_date}")
    Date getReturnDate();
    @Value("#{target.code}")
    String getCode();
    @Value("#{target.book_number}")
    int getBookNumber();
}
