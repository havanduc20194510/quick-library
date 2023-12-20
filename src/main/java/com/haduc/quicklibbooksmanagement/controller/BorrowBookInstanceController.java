package com.haduc.quicklibbooksmanagement.controller;

import com.haduc.quicklibbooksmanagement.service.BorrowBookInstanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/borrow-book-instance")
@AllArgsConstructor
public class BorrowBookInstanceController {
    BorrowBookInstanceService borrowBookInstanceService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam Long libraryId) {
        String message = borrowBookInstanceService.create(userId, bookId, libraryId);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}
