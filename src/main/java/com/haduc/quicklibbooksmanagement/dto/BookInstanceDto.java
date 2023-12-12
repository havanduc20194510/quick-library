package com.haduc.quicklibbooksmanagement.dto;

import com.haduc.quicklibbooksmanagement.entity.Book;
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
}