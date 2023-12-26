package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.*;
import com.haduc.quicklibbooksmanagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {

    BookDto insertBook(BookDto bookDto, MultipartFile image) throws IOException;
    Page<ResultDto> getAll(int page, int size);

    List<ResultConvertDto> getAllConvert();
    BookInstanceDto getById(Long id);
    Page<ResultDto> search(String title, String authorName, Integer publishYear, String libraryName, Long categoryId, int page, int size);

    Page<Book> findByTitleAndCategoryId(String title, Long categoryId, int page, int size);

}
