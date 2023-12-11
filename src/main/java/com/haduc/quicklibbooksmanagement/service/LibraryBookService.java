package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.LibraryBookDto;
import com.haduc.quicklibbooksmanagement.entity.LibraryBook;

import java.util.List;

public interface LibraryBookService {
    LibraryBookDto insertLibraryBook(LibraryBookDto libraryBookDto);
    List<LibraryBookDto> getAll();
}
