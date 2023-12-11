package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.LibraryDto;

import java.util.List;

public interface LibraryService {
    LibraryDto insertLibrary(LibraryDto libraryDto);
    List<LibraryDto> getAll();
}
