package com.haduc.quicklibbooksmanagement.service.impl;

import com.haduc.quicklibbooksmanagement.dto.CategoryDto;
import com.haduc.quicklibbooksmanagement.entity.Category;
import com.haduc.quicklibbooksmanagement.mapper.CategoryMapper;
import com.haduc.quicklibbooksmanagement.repository.CategoryRepository;
import com.haduc.quicklibbooksmanagement.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    private CategoryMapper categoryMapper;

    @Override
    public CategoryDto insertCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        Date currentDate = new Date();
        category.setCreated_at(currentDate);
        category.setUpdated_at(currentDate);
        Category savedCategroy = categoryRepository.save(category);
        CategoryDto savedCategoryDto = categoryMapper.toCategoryDto(savedCategroy);
        return savedCategoryDto;
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(category -> {
                    CategoryDto categoryDto = categoryMapper.toCategoryDto(category);
                    Long parentCategoryId = categoryDto.getParent_category_id();
                    if(parentCategoryId != null && parentCategoryId != -1L) {
                        Category parentCategory = categoryRepository.findById(parentCategoryId).get();
                        categoryDto.setParent_category_name(parentCategory.getName());
                    }
                    return categoryDto;
                })
                .collect(java.util.stream.Collectors.toList());
    }
}
