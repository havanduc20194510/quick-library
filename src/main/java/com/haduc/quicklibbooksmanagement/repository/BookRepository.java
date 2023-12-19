package com.haduc.quicklibbooksmanagement.repository;

import com.haduc.quicklibbooksmanagement.dto.BookInfoDto;
import com.haduc.quicklibbooksmanagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {
    @Query(value = "SELECT " +
            "b.id as book_id, " +
            "b.title as title, " +
            "b.description as description, " +
            "b.cover_image_url as cover_image_url, " +
            "b.publish_year as publish_year, " +
            "b.publisher as publisher," +
            "b.language as language, " +
            "b.page_number as page_number, " +
            "b.created_at as created_at," +
            "b.updated_at as updated_at," +
            "c.id as category_id," +
            "c.name as category_name," +
            "c.parent_category_id as parent_category_id," +
            "l.id as library_id, " +
            "l.name as library_name, " +
            "l.location as location, " +
            "lb.code as code, " +
            "lb.status as status " +
            "FROM books b " +
            "JOIN categories c " +
            "ON b.category_id = c.id " +
            "JOIN book_instances lb " +
            "ON b.id = lb.book_id " +
            "JOIN Libraries l " +
            "ON lb.library_id = l.id " +
            "ORDER BY title ASC",
            nativeQuery = true)
    Page<BookInfoDto> getBooksAndLibraries(Pageable pageable);
    Page<Book> findByTitleContainsIgnoreCaseAndCategoryId(String title, Long categoryId, Pageable pageable);

    @Query(value = "SELECT * FROM books WHERE publish_year = :publishYear AND category_id = :categoryId", nativeQuery = true)
    Page<Book> findByPublishYearAndAndCategoryId(int publishYear, Long categoryId, Pageable pageable);

    @Query(value = "SELECT DISTINCT b.* FROM books b JOIN categories c ON b.category_id = c.id JOIN book_instances lb ON b.id = lb.book_id JOIN libraries l ON lb.library_id = l.id WHERE (b.category_id = :categoryId OR c.parent_category_id = :categoryId) AND LOWER(l.name) LIKE LOWER(CONCAT('%', :libraryName, '%'))", nativeQuery = true)
    Page<Book> findByCategoryIdAndLibraryName(Long categoryId, String libraryName, Pageable pageable);
    @Query(value = "SELECT DISTINCT b.* FROM books b JOIN categories c ON b.category_id = c.id JOIN authors_books ab ON b.id = ab.book_id JOIN authors a ON ab.author_id = a.id WHERE (b.category_id = :categoryId OR c.parent_category_id = :categoryId) AND LOWER(a.name) LIKE LOWER(CONCAT('%', :authorName, '%'))", nativeQuery = true)
    Page<Book> findByCategoryIdAndAuthorName(Long categoryId, String authorName, Pageable pageable);

    Page<Book> findByTitleContainsIgnoreCase(String title, Pageable pageable);

    @Query(value = "SELECT * FROM books WHERE publish_year = :publishYearInt", nativeQuery = true)
    Page<Book> findByPublishYear(int publishYearInt, Pageable pageable);

    @Query(value = "SELECT DISTINCT b.* FROM books b JOIN authors_books ab ON b.id = ab.book_id JOIN authors a ON ab.author_id = a.id WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :authorName, '%'))", nativeQuery = true)
    Page<Book> findByAuthorName(String authorName, Pageable pageable);
    @Query(value = "SELECT DISTINCT b.* FROM books b JOIN book_instances lb ON b.id = lb.book_id JOIN libraries l ON lb.library_id = l.id WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :libraryName, '%'))", nativeQuery = true)
    Page<Book> findByLibraryName(String libraryName, Pageable pageable);
    Page<Book> findByCategoryId(Long categoryId, Pageable pageable);
}
