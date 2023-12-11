package com.haduc.quicklibbooksmanagement.controller;

import com.haduc.quicklibbooksmanagement.dto.AuthorBookDto;
import com.haduc.quicklibbooksmanagement.service.AuthorBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/author-book")
public class AuthorBookController {
    private AuthorBookService authorBookService;
    @PostMapping("/create")
    public ResponseEntity<AuthorBookDto> insertAuthorBook(@RequestBody AuthorBookDto authorBookDto) {
        AuthorBookDto authorBookDtoSaved = authorBookService.insertAuthorBook(authorBookDto);
        return new ResponseEntity<>(authorBookDtoSaved, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AuthorBookDto>> getAll() {
        List<AuthorBookDto> authorBookDto = authorBookService.getAll();
        return new ResponseEntity<>(authorBookDto, HttpStatus.OK);
    }

}
