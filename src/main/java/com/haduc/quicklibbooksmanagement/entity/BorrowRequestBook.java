package com.haduc.quicklibbooksmanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "borrow_request_books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequestBook {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "borrow_request_id")
    private BorrowRequest borrowRequest;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
