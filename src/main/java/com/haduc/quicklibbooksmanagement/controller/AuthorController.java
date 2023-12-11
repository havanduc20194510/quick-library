package com.haduc.quicklibbooksmanagement.controller;

import com.haduc.quicklibbooksmanagement.dto.AuthorDto;
import com.haduc.quicklibbooksmanagement.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/author")
public class AuthorController {

    private AuthorService authorService;

    @PostMapping("/create")
    public ResponseEntity<AuthorDto> insertAuthor(@RequestBody AuthorDto authorDto) {
        AuthorDto authorDtoSaved = authorService.insertAuthor(authorDto);
        return new ResponseEntity<>(authorDtoSaved, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AuthorDto>> getAll() {
        List<AuthorDto> authorLists = authorService.getAll();
        return new ResponseEntity<>(authorLists, HttpStatus.OK);
    }
}
