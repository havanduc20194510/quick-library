package com.haduc.quicklibbooksmanagement.controller;

import com.haduc.quicklibbooksmanagement.dto.BorrowBookInstanceDto;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestBookInfo;
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
    public ResponseEntity<List<BorrowRequestBookInfo>> getBorrowBookInstancesByBorrowRequestId(@RequestParam("user_id") Long userId,@RequestParam("borrow_request_id") Long borrowRequestId){
        List<BorrowRequestBookInfo> borrowBookInstanceDtoList = borrowBookInstanceService.getBorrowBookInstancesByBorrowRequestId(userId, borrowRequestId);
        return new ResponseEntity<>(borrowBookInstanceDtoList, HttpStatus.OK);
    }
}
