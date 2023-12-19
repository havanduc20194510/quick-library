package com.haduc.quicklibbooksmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "book_instances",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"code", "library_id"})
)
public class LibraryBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;

    @Column(nullable = false, length = 255)
    private String status;

    @Column(nullable = false, length = 255)
    private String code;

    @Column(nullable = false)
    private int quantity;

    private Date created_at;

    private Date updated_at;
}
