package com.haduc.quicklibbooksmanagement.repository;

import com.haduc.quicklibbooksmanagement.dto.BorrowBookInstanceDto;
import com.haduc.quicklibbooksmanagement.entity.BorrowBookInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowBookInstanceRepository extends JpaRepository<BorrowBookInstance, Long> {
    List<BorrowBookInstanceDto> findByBorrowRequestId(Long borrowId);

}
