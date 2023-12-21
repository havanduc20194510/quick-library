package com.haduc.quicklibbooksmanagement.dto;

import com.haduc.quicklibbooksmanagement.entity.BorrowStatus;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface BorrowRequestBookInfo {
    @Value("#{target.book_id}")
    Long getId();
    @Value("#{target.title}")
    String getTitle();
    @Value("#{target.publisher}")
    String getPushlisher();
    @Value("#{target.book_image}")
    String getImage();
    @Value("#{target.category_id}")
    String getCategoryId();
    @Value("#{target.category_name}")
    String getCategoryName();
    @Value("#{target.parent_category_id}")
    Long getParentCategoryId();
    @Value("#{target.library_id}")
    Long getLibraryId();
    @Value("#{target.library_name}")
    String getLibraryName();
    @Value("#{target.borrow_request_id}")
    Long getBorrowRequestId();
    @Value("#{target.request_date}")
    Date getRequestDate();
    @Value("#{target.borrow_date}")
    Date getBorrowDate();
    @Value("#{target.request_due_date}")
    Date getRequestDueDate();
    @Value("#{target.status_request}")
    String getStatus();
    @Value("#{target.borrow_book_instance_id}")
    Long getBorrowBookInstanceId();
}
