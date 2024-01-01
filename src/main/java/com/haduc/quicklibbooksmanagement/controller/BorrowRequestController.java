package com.haduc.quicklibbooksmanagement.controller;

import com.cloudinary.utils.StringUtils;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestDto;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestInfo;
import com.haduc.quicklibbooksmanagement.dto.BorrowRequestItem;
import com.haduc.quicklibbooksmanagement.service.BorrowRequestService;
import com.haduc.quicklibbooksmanagement.util.DateTimeUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<BorrowRequestItem>> getAllBorrowRequest(@RequestParam("libraryId") Long libraryId){
        List<BorrowRequestItem> borrowRequestList = borrowRequestService.getAllBorrowRequestItems(libraryId);
        return new ResponseEntity<>(borrowRequestList, HttpStatus.OK);
    }

    @GetMapping("/list/{user_id}")
    public ResponseEntity<List<BorrowRequestInfo>> getBorrowRequestByUserId(@PathVariable("user_id") Long userId){
        List<BorrowRequestInfo> borrowRequestList = borrowRequestService.getBorrowRequestsByUserId(userId);
        return new ResponseEntity<>(borrowRequestList, HttpStatus.OK);
    }

    @PostMapping("/sent/{borrow_request_id}")
    public ResponseEntity<String> sentBorrowRequest(@PathVariable("borrow_request_id") Long borrowRequestId, @RequestParam("borrow_date") String borrowDateStr, @RequestParam("request_due_date") String requestDueDateStr) {
        try {
            if (StringUtils.isBlank(borrowDateStr) || StringUtils.isBlank(requestDueDateStr)) {
                return new ResponseEntity<>("Date is null", HttpStatus.BAD_REQUEST);
            }
            if(borrowDateStr.equals("NaN-NaN-NaN") || requestDueDateStr.equals("NaN-NaN-NaN")){
                return new ResponseEntity<>("Date is invalid", HttpStatus.BAD_REQUEST);
            }
            Date borrowDate = DateTimeUtils.convertStringToDate(borrowDateStr);
            Date requestDueDate = DateTimeUtils.convertStringToDate(requestDueDateStr);

            if (borrowDate == null || requestDueDate == null) {
                return new ResponseEntity<>("Date is invalid", HttpStatus.BAD_REQUEST);
            }

            String message = borrowRequestService.sentBorrowRequest(borrowRequestId, borrowDate, requestDueDate);
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>("Invalid date format", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateBorrowRequest(@RequestParam("code") String code) {
        int check = borrowRequestService.acceptBorrowRequest(code);
        if(check == -1) return new ResponseEntity<>("Code is invalid", HttpStatus.NOT_FOUND);
        else if(check == 0) return new ResponseEntity<>("Borrow request has been accepted", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>("Accept borrow request successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{borrow_request_id}")
    public ResponseEntity<String> deleteBorrowRequest(@PathVariable("borrow_request_id") Long borrowRequestId) {
        String message = borrowRequestService.deleteBorrowRequest(borrowRequestId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBorrowRequest(@RequestParam("code") String code) {
        boolean check = borrowRequestService.returnBorrowRequest(code);
        if(check) return new ResponseEntity<>("Return borrow request successfully", HttpStatus.OK);
        else return new ResponseEntity<>("Code is invalid", HttpStatus.NOT_FOUND);
    }
}
