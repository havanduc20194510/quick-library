package com.haduc.quicklibbooksmanagement.repository;

import com.haduc.quicklibbooksmanagement.entity.AuthorBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface AuthorBookRepository extends JpaRepository<AuthorBook, Long> {
    List<AuthorBook> findByBookId(Long id);
}
