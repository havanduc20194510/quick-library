package com.haduc.quicklibbooksmanagement.dto;

import com.haduc.quicklibbooksmanagement.entity.BorrowBookInstance;
import com.haduc.quicklibbooksmanagement.entity.BorrowStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequestDto {
    private Long id;
    private UserDto user;
    private BorrowStatus status;
    private LibraryDto library;
    private Date borrowDate;
    private Date requestDueDate;
    private Date returnDate;
    private String code;
    private Date createdAt;
    private Date updatedAt;

    private List<BorrowBookInstance> borrowBookInstances;
}
