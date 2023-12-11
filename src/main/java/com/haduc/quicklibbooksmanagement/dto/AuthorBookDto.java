package com.haduc.quicklibbooksmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBookDto {
    private Long id;
    private BookDto book;
    private AuthorDto author;
    private Date created_at;
    private Date updated_at;
}
