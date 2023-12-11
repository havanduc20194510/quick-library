package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorDto insertAuthor(AuthorDto authorDto);
    List<AuthorDto> getAll();
}
