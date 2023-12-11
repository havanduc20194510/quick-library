package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.AuthorBookDto;

import java.util.List;

public interface AuthorBookService {
    AuthorBookDto insertAuthorBook(AuthorBookDto authorBookDto);
    List<AuthorBookDto> getAll();
}
