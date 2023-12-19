package com.haduc.quicklibbooksmanagement.controller;

import com.haduc.quicklibbooksmanagement.dto.BorrowRequestDto;
import com.haduc.quicklibbooksmanagement.service.BorrowRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/borrow-request")
public class BorrowRequestController {
    BorrowRequestService borrowRequestService;

    @PostMapping("/create")
    public ResponseEntity<String> createBorrowRequest(@RequestBody BorrowRequestDto borrowRequestDto, @RequestParam("user_id") Long userId, @RequestParam("library_id") Long libraryId) {
        String message = borrowRequestService.createBorrowRequest(borrowRequestDto, userId, libraryId);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BorrowRequestDto>> getAllBorrowRequest(){
        List<BorrowRequestDto> borrowRequestList = borrowRequestService.getAllBorrowRequests();
        return new ResponseEntity<>(borrowRequestList, HttpStatus.OK);
    }

}
