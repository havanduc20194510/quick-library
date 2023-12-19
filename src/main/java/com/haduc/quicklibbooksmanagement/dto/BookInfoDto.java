package com.haduc.quicklibbooksmanagement.dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface BookInfoDto {
    @Value("#{target.book_id}")
    Long getId();
    String getTitle();
    String getDescription();
    String getCover_image_url();
    int getPublish_year();
    String getPublisher();
    String getLanguage();
    int getPage_number();
    Date getCreated_at();
    Date getUpdated_at();

    @Value("#{target.category_id}")
    Long getCategory_id();
    @Value("#{target.category_name}")
    String getCategory_name();
    @Value("#{target.parent_category_id}")
    Long getParent_category_id();

    @Value("#{target.library_id}")
    Long getLibrary_id();

    @Value("#{target.library_name}")
    String getLibrary_name();

    @Value("#{target.location}")
    String getLocation();

    @Value("#{target.code}")
    String getCode();
    @Value("#{target.status}")
    String getStatus();
}
