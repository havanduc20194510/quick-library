package com.haduc.quicklibbooksmanagement.controller;

import com.haduc.quicklibbooksmanagement.dto.CategoryDto;
import com.haduc.quicklibbooksmanagement.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> insertCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategoryDto = categoryService.insertCategory(categoryDto);
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryDto>> getAll() {
        List<CategoryDto> categoryDtos = categoryService.getAll();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }
}
