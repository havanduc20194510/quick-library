package com.haduc.quicklibbooksmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 255)
    private String title;
    @Column(nullable = false, length = 255)
    private String description;
    @Column(nullable = false)
    private int quantity;
    private String cover_image_url;
    @ManyToOne
    private Category category;
    @Column(nullable = false)
    private int publish_year;
    @Column(nullable = false, length = 255)
    private String publisher;
    @Column(nullable = false, length = 255)
    private String language;
    @Column(nullable = false)
    private int page_number;
    private Date created_at;
    private Date updated_at;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuthorBook> authorBooks;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibraryBook> libraryBooks;
}
