package com.haduc.quicklibbooksmanagement.repository;

import com.haduc.quicklibbooksmanagement.entity.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryBookRepository extends JpaRepository<LibraryBook, Long> {
    LibraryBook findByBookIdAndLibrary_Id(Long bookId, Long libraryId);
}
