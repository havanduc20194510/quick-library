package com.haduc.quicklibbooksmanagement.controller;

import com.haduc.quicklibbooksmanagement.dto.BorrowBookInfor;
import com.haduc.quicklibbooksmanagement.service.BorrowBookInstanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/borrow-book-instance")
@AllArgsConstructor
public class BorrowBookInstanceController {
    BorrowBookInstanceService borrowBookInstanceService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam Long libraryId) {
        String message = borrowBookInstanceService.create(userId, bookId, libraryId);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BorrowBookInfor>> getBorrowBookInstancesByBorrowRequestId(@RequestParam("borrow_request_id") Long borrowRequestId){
        List<BorrowBookInfor> borrowBookInstanceDtoList = borrowBookInstanceService.getBorrowBookInstancesByBorrowRequestId(borrowRequestId);
        return new ResponseEntity<>(borrowBookInstanceDtoList, HttpStatus.OK);
    }
    @GetMapping("/list/{user_id}")
    public ResponseEntity<List<BorrowBookInfor>> getBorrowBookInstancesByUserId(@PathVariable("user_id") Long userId){
        List<BorrowBookInfor> borrowBookInstanceDtoList = borrowBookInstanceService.getBorrowBookInstancesByUserId(userId);
        return new ResponseEntity<>(borrowBookInstanceDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{borrow_book_instance_id}")
    public ResponseEntity<String> deleteBookInRequest(@PathVariable("borrow_book_instance_id") Long borrowBookInstanceId){
        String message = borrowBookInstanceService.deleteBookInRequest(borrowBookInstanceId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
