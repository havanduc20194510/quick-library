package com.haduc.quicklibbooksmanagement.dto;

import com.haduc.quicklibbooksmanagement.entity.Book;
import com.haduc.quicklibbooksmanagement.entity.Library;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBookDto {
    private Long id;
    private BookDto book;
    private LibraryDto library;
    private String status;
    private String code;
    private int quantity;
    private Date created_at;
    private Date updated_at;
}
