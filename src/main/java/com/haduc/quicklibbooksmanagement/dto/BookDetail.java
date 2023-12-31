package com.haduc.quicklibbooksmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDetail {
    private Long id;
    private String title;
    private String description;
    private String cover_image_url;
    private int publish_year;
    private String publisher;
    private String language;
    private int page_number;
    private Date created_at;
    private Date updated_at;

    private Long category_id;
    private String category_name;
    private Long parent_category_id;
    private String parent_category_name;

    private Long library_id;
    private String library_name;
    private String location;
    private String code;
    private String status;
}
