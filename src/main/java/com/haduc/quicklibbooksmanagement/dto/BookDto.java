package com.haduc.quicklibbooksmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String description;
    private int quantity;
    private String cover_image_url;
    private CategoryDto category;
    private int publish_year;
    private String publisher;
    private String language;
    private int page_number;
    private Date created_at;
    private Date updated_at;

    //private List<AuthorBookDto> authorBookDtos;
    //private List<LibraryBookDto> libraryBookDtos;
}
