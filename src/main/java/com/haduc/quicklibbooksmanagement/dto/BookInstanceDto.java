package com.haduc.quicklibbooksmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class BookInstanceDto {
    private BookDto book;
    private List<AuthorDto> authors;
    private LibraryDto libraryDto;
    private List<BookDto> ortherBooks;
}
