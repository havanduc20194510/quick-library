package com.haduc.quicklibbooksmanagement.repository;

import com.haduc.quicklibbooksmanagement.dto.BorrowRequestBookInfo;
import com.haduc.quicklibbooksmanagement.entity.BorrowBookInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowBookInstanceRepository extends JpaRepository<BorrowBookInstance, Long> {
    BorrowBookInstance findByBorrowRequest_IdAndLibraryBook_Id(Long bookId, Long libraryId);
    @Query(value = "SELECT b.id as book_id, b.title as title, b.publisher as publisher, b.cover_image_url as book_image, c.id as category_id, c.name as category_name, c.parent_category_id as parent_category_id, l.id as library_id, l.name as library_name, br.id as borrow_request_id, br.created_at as request_date, brd.id as borrow_book_instance_id FROM borrow_request br JOIN borrow_book_instances brd ON br.id = brd.borrow_id JOIN book_instances lb ON brd.book_instance_id = lb.id JOIN books b ON lb.book_id = b.id JOIN libraries l ON lb.library_id = l.id JOIN categories c ON b.category_id = c.id  WHERE br.user_id = :userId AND br.id = :requestId", nativeQuery = true)
    List<BorrowRequestBookInfo> findBooksByBorrowRequestId(Long userId, Long requestId);

}
