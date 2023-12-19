package com.haduc.quicklibbooksmanagement.dto;

import com.haduc.quicklibbooksmanagement.entity.BorrowStatus;
import com.haduc.quicklibbooksmanagement.entity.Library;
import com.haduc.quicklibbooksmanagement.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequestDto {
    private Long id;
    private User user;
    private BorrowStatus status;
    private Library library;
    private Date borrowDate;
    private Date requestDueDate;
    private Date returnDate;
    private Date createdAt;
    private Date updatedAt;
}
