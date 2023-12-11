package com.haduc.quicklibbooksmanagement.controller;

import com.haduc.quicklibbooksmanagement.dto.LibraryDto;
import com.haduc.quicklibbooksmanagement.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/library")
public class LibraryController {
    private LibraryService libraryService;

    @PostMapping("/create")
    public ResponseEntity<LibraryDto> insertLibrary(@RequestBody LibraryDto libraryDto) {
        LibraryDto libraryDtoSaved = libraryService.insertLibrary(libraryDto);
        return new ResponseEntity<>(libraryDtoSaved, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<LibraryDto>> getAll() {
        List<LibraryDto> libraryDtos = libraryService.getAll();
        return new ResponseEntity<>(libraryDtos, HttpStatus.OK);
    }
}
