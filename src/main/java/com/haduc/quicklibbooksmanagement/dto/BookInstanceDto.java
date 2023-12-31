package com.haduc.quicklibbooksmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookInstanceDto {
    private BookDto book;
    private List<AuthorDto> authors;
    private List<LibraryBookInfoDto> librarys;
    private List<BookDto> ortherBooks;
}
