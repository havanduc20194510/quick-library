package com.haduc.quicklibbooksmanagement.dto;

import com.haduc.quicklibbooksmanagement.entity.BorrowStatus;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface BorrowRequestItem {
    @Value("#{target.request_id}")
    Long getId();

    @Value("#{target.user_id}")
    Long getUserId();

    @Value("#{target.user_name}")
    String getUserName();

    @Value("#{target.status}")
    BorrowStatus getStatus();

    @Value("#{target.borrow_date}")
    Date getBorrowDate();

    @Value("#{target.request_due_date}")
    Date getRequestDueDate();

    @Value("#{target.book_number}")
    int getBookNumber();
}
