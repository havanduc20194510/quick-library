package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.BookDto;
import com.haduc.quicklibbooksmanagement.dto.CategoryDto;
import com.haduc.quicklibbooksmanagement.entity.Book;
import com.haduc.quicklibbooksmanagement.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-24T15:06:43+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public Book toBook(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( bookDto.getId() );
        book.setTitle( bookDto.getTitle() );
        book.setDescription( bookDto.getDescription() );
        book.setCover_image_url( bookDto.getCover_image_url() );
        book.setCategory( categoryDtoToCategory( bookDto.getCategory() ) );
        book.setPublish_year( bookDto.getPublish_year() );
        book.setPublisher( bookDto.getPublisher() );
        book.setLanguage( bookDto.getLanguage() );
        book.setPage_number( bookDto.getPage_number() );
        book.setCreated_at( bookDto.getCreated_at() );
        book.setUpdated_at( bookDto.getUpdated_at() );

        return book;
    }

    @Override
    public BookDto toBookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        bookDto.setId( book.getId() );
        bookDto.setTitle( book.getTitle() );
        bookDto.setDescription( book.getDescription() );
        bookDto.setCover_image_url( book.getCover_image_url() );
        bookDto.setCategory( categoryToCategoryDto( book.getCategory() ) );
        bookDto.setPublish_year( book.getPublish_year() );
        bookDto.setPublisher( book.getPublisher() );
        bookDto.setLanguage( book.getLanguage() );
        bookDto.setPage_number( book.getPage_number() );
        bookDto.setCreated_at( book.getCreated_at() );
        bookDto.setUpdated_at( book.getUpdated_at() );

        return bookDto;
    }

    protected Category categoryDtoToCategory(CategoryDto categoryDto) {
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

    protected CategoryDto categoryToCategoryDto(Category category) {
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
}
