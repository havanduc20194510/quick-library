package com.haduc.quicklibbooksmanagement.dto;

import com.haduc.quicklibbooksmanagement.entity.BorrowRequest;
import com.haduc.quicklibbooksmanagement.entity.LibraryBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowBookInstanceDto {

    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private BorrowRequest borrowRequest;

    private LibraryBook libraryBook;

}
