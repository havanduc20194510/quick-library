package com.haduc.quicklibbooksmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haduc.quicklibbooksmanagement.dto.BookDto;
import com.haduc.quicklibbooksmanagement.dto.BookInstanceDto;
import com.haduc.quicklibbooksmanagement.dto.ResultDto;
import com.haduc.quicklibbooksmanagement.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@AllArgsConstructor
public class BookController {
    private BookService bookService;

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<BookDto> insertBook(@RequestParam("bookDto") String bookDtoJson, @RequestParam("image") MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BookDto bookDto = objectMapper.readValue(bookDtoJson, BookDto.class);
        BookDto savedBookDto = bookService.insertBook(bookDto, image);
        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    ResponseEntity<List<ResultDto>> getAll() {
        List<ResultDto> books = bookService.getAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<BookInstanceDto> getById(@PathVariable("id") Long id) {
        BookInstanceDto book = bookService.getById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/search")
    ResponseEntity<List<ResultDto>> search(@RequestParam(value = "title", required = false) String title,
                                      @RequestParam(value = "authorName", required = false) String authorName,
                                      @RequestParam(value = "publishYear", required = false) Integer publishYear,
                                      @RequestParam(value = "libraryName", required = false) String libraryName,
                                      @RequestParam(value = "category", required = false) String category) {
        List<ResultDto> books = bookService.search(title, authorName, publishYear, libraryName, category);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
