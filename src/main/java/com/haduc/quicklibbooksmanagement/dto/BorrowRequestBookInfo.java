package com.haduc.quicklibbooksmanagement.dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface BorrowRequestBookInfo {
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
    String getParentCategoryId();
    String getParentCategoryName();
    @Value("#{target.borrow_request_id}")
    Long getBorrowRequestId();
    @Value("#{target.request_date}")
    Date getRequestDate();

}
