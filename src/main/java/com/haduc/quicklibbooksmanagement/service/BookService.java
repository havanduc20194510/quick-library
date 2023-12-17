package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {

    BookDto insertBook(BookDto bookDto, MultipartFile image) throws IOException;
    List<ResultDto> getAll();
    Page<ResultConvertDto> getAllConvert(int page, int size);

    BookInstanceDto getById(Long id);

    List<ResultDto> search(String title, String authorName, Integer publishYear, String libraryName, Long categoryId);
    Page<ResultConvertDto> searchByParam(String title, String authorName, Integer publishYear, String libraryName, Long categoryId, int page, int size);

    Page<BookDetail> getAllBook(int page, int size);
}
