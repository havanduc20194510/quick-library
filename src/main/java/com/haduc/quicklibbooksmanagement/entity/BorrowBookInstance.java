package com.haduc.quicklibbooksmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "borrow_book_instances")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowBookInstance {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "borrow_id")
    private BorrowRequest borrowRequest;

    @ManyToOne
    @JoinColumn(name = "book_instance_id")
    private LibraryBook libraryBook;

}
