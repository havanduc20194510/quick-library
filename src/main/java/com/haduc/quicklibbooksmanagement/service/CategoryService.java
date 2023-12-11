package com.haduc.quicklibbooksmanagement.service;

import com.haduc.quicklibbooksmanagement.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto insertCategory(CategoryDto categoryDto);
    List<CategoryDto> getAll();
}
