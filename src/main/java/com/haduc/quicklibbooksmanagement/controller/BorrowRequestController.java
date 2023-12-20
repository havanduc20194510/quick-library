package com.haduc.quicklibbooksmanagement.controller;

import com.haduc.quicklibbooksmanagement.dto.BorrowRequestDto;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestInfo;
import com.haduc.quicklibbooksmanagement.service.BorrowRequestService;
import com.haduc.quicklibbooksmanagement.util.DateTimeUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
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

    @GetMapping("/list/{user_id}")
    public ResponseEntity<List<BorrowRequestInfo>> getBorrowRequestByUserId(@PathVariable("user_id") Long userId){
        List<BorrowRequestInfo> borrowRequestList = borrowRequestService.getBorrowRequestsByUserId(userId);
        return new ResponseEntity<>(borrowRequestList, HttpStatus.OK);
    }

    @PostMapping("/sent/{borrow_request_id}")
    public ResponseEntity<String> sentBorrowRequest(@PathVariable("borrow_request_id") Long borrowRequestId, @RequestParam("borrow_date") String borrowDateStr, @RequestParam("request_due_date") String requestDueDateStr) throws ParseException {
        Date borrowDate = DateTimeUtils.convertStringToDate(borrowDateStr);
        Date requestDueDate = DateTimeUtils.convertStringToDate(requestDueDateStr);
        String message = borrowRequestService.sentBorrowRequest(borrowRequestId, borrowDate, requestDueDate);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
