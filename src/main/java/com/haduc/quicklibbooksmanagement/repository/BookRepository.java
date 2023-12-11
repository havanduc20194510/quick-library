package com.haduc.quicklibbooksmanagement.repository;

import com.haduc.quicklibbooksmanagement.entity.Book;
import com.haduc.quicklibbooksmanagement.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {
    /*List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorBooksAuthorNameIgnoreCase(String authorName);

    List<Book> findByPublishYear(Integer publishYear);

    List<Book> findByLibraryBooksLibraryNameIgnoreCase(String libraryName);

    List<Book> findByCategoryIn(List<Category> categories);*/
}
