package com.haduc.quicklibbooksmanagement.repository;

import com.haduc.quicklibbooksmanagement.dto.BorrowRequestBookInfo;
import com.haduc.quicklibbooksmanagement.entity.BorrowBookInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowBookInstanceRepository extends JpaRepository<BorrowBookInstance, Long> {
    BorrowBookInstance findByBorrowRequest_IdAndLibraryBook_Id(Long bookId, Long libraryId);
 /*   @Query("SELECT b.id as book_id, b.title as title, b.publisher as publisher, b.cover_image_url as book_image, c.id as category_id, c.name as category_name, c.parent_category_id as parent_category_id,   FROM BorrowBookInstance b WHERE b.borrowRequest.id = ?1", nativeQuery = true)
    List<BorrowRequestBookInfo> findBooksByBorrowRequestId(Long borrowId);*/
}
