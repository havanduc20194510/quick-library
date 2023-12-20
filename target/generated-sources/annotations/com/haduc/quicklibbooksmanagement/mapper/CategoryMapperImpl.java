package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.CategoryDto;
import com.haduc.quicklibbooksmanagement.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-20T11:45:15+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto toCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId( category.getId() );
        categoryDto.setName( category.getName() );
        categoryDto.setParent_category_id( category.getParent_category_id() );
        categoryDto.setCreated_at( category.getCreated_at() );
        categoryDto.setUpdated_at( category.getUpdated_at() );

        return categoryDto;
    }

    @Override
    public Category toCategory(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( categoryDto.getId() );
        category.setName( categoryDto.getName() );
        category.setParent_category_id( categoryDto.getParent_category_id() );
        category.setCreated_at( categoryDto.getCreated_at() );
        category.setUpdated_at( categoryDto.getUpdated_at() );

        return category;
    }
}
