package com.haduc.quicklibbooksmanagement.repository;

import com.haduc.quicklibbooksmanagement.dto.BookInfoDto;
import com.haduc.quicklibbooksmanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {
    /*List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorBooksAuthorNameIgnoreCase(String authorName);

    List<Book> findByPublishYear(Integer publishYear);

    List<Book> findByLibraryBooksLibraryNameIgnoreCase(String libraryName);

    List<Book> findByCategoryIn(List<Category> categories);*/
    // trả về kết quả truy van vao List<BookInfoDto>


    @Query("SELECT b.id,b.title,b.description,b.quantity,b.cover_image_url,b.publisher,b.publish_year,b.language,b.page_number,b.created_at,b.updated_at,c.id, c.name, c.parent_category_id, l.id, l.name, l.location FROM Book b JOIN Category c ON b.category.id = c.id JOIN LibraryBook lb ON b.id = lb.book.id JOIN Library l ON lb.book.id = l.id")
    List<BookInfoDto> getBooksAndLibraries();
}
