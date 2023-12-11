package com.haduc.quicklibbooksmanagement.service.impl;

import com.haduc.quicklibbooksmanagement.dto.AuthorDto;
import com.haduc.quicklibbooksmanagement.entity.Author;
import com.haduc.quicklibbooksmanagement.mapper.AuthorMapper;
import com.haduc.quicklibbooksmanagement.repository.AuthorRepository;
import com.haduc.quicklibbooksmanagement.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    private AuthorMapper authorMapper;

    @Override
    public AuthorDto insertAuthor(AuthorDto authorDto) {
        Author author = authorMapper.toAuthor(authorDto);
        Author authorSaved = authorRepository.save(author);
        AuthorDto authorDtoSaved = authorMapper.toAuthorDto(authorSaved);
        return authorDtoSaved;
    }

    @Override
    public List<AuthorDto> getAll() {
        List<Author> authorList = authorRepository.findAll();
        return authorList.stream()
                .map(author -> authorMapper.toAuthorDto(author))
                .collect(Collectors.toList());
    }
}
