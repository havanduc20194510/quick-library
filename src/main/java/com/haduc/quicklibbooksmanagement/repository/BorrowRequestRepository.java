package com.haduc.quicklibbooksmanagement.repository;

import com.haduc.quicklibbooksmanagement.dto.BorrowRequestInfo;
import com.haduc.quicklibbooksmanagement.entity.BorrowRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, Long> {
    List<BorrowRequest> findByUserIdAndLibraryId(Long user_id, Long library_id);
    @Query(value = "SELECT br.id as id, br.user_id as user_id, br.status as status, br.library_id as library_id, l.name as library_name, br.borrow_date as borrow_date, br.request_due_date as request_due_date, br.return_date as return_date, COUNT(brb.book_instance_id) as book_number FROM borrow_book_instances brb JOIN borrow_request br ON brb.borrow_id = br.id JOIN libraries l ON br.library_id = l.id WHERE br.user_id = :userId GROUP BY (br.id,br.library_id,l.name)", nativeQuery = true)
    List<BorrowRequestInfo> findByUserIdAndCount(Long userId);

}
