package com.haduc.quicklibbooksmanagement.controller;

import com.haduc.quicklibbooksmanagement.dto.LibraryBookDto;
import com.haduc.quicklibbooksmanagement.service.LibraryBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/library-book")
public class LibraryBookController {
    private LibraryBookService libraryBookService;

    @PostMapping("/create")
    public ResponseEntity<LibraryBookDto> insertLibraryBook(@RequestBody LibraryBookDto libraryBookDto) {
        LibraryBookDto libraryBookDtoSaved = libraryBookService.insertLibraryBook(libraryBookDto);
        return new ResponseEntity<>(libraryBookDtoSaved, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<LibraryBookDto>> getAll() {
        List<LibraryBookDto> libraryBookDto = libraryBookService.getAll();
        return new ResponseEntity<>(libraryBookDto, HttpStatus.OK);
    }
}
