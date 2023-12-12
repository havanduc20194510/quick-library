package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {

    BookDto insertBook(BookDto bookDto, MultipartFile image) throws IOException;
    List<ResultDto> getAll();

    BookInstanceDto getById(Long id);

  /*  List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorBooksAuthorNameIgnoreCase(String authorName);

    List<Book> findByPublishYear(Integer publishYear);

    List<Book> findByLibraryBooksLibraryNameIgnoreCase(String libraryName);

    List<Book> findByCategoryIn(List<Category> categories);*/

    List<ResultDto> search(String title, String authorName, Integer publishYear, String libraryName, String categories);

    List<BookInfoResultDto> getBooksAndLibrariesInfo();
}
