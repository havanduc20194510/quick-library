package com.haduc.quicklibbooksmanagement.service.impl;

import com.haduc.quicklibbooksmanagement.dto.AuthorBookDto;
import com.haduc.quicklibbooksmanagement.entity.AuthorBook;
import com.haduc.quicklibbooksmanagement.mapper.AuthorBookMapper;
import com.haduc.quicklibbooksmanagement.repository.AuthorBookRepository;
import com.haduc.quicklibbooksmanagement.service.AuthorBookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
public class AuthorBookServiceImpl implements AuthorBookService {
    private AuthorBookRepository authorBookRepository;
    private AuthorBookMapper authorBookMapper;

    @Override
    public AuthorBookDto insertAuthorBook(AuthorBookDto authorBookDto) {
        Date currentTime = new Date();
        authorBookDto.setCreated_at(currentTime);
        authorBookDto.setUpdated_at(currentTime);
        AuthorBook authorBook = authorBookMapper.toAuthorBook(authorBookDto);
        AuthorBook authorBookSaved = authorBookRepository.save(authorBook);
        AuthorBookDto authorBookDtoSaved = authorBookMapper.toAuthorBookDto(authorBookSaved);
        return authorBookDtoSaved;
    }

    @Override
    public List<AuthorBookDto> getAll() {
        List<AuthorBook> authorBookList = authorBookRepository.findAll();
        return authorBookList.stream()
                .map(authorBook -> authorBookMapper.toAuthorBookDto(authorBook))
                .collect(java.util.stream.Collectors.toList());
    }
}
