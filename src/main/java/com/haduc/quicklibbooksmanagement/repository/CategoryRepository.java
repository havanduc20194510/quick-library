package com.haduc.quicklibbooksmanagement.repository;

import com.haduc.quicklibbooksmanagement.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
