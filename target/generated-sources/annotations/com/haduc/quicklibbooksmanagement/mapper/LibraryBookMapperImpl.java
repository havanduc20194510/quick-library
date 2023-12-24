package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.BookDto;
import com.haduc.quicklibbooksmanagement.dto.CategoryDto;
import com.haduc.quicklibbooksmanagement.dto.LibraryBookDto;
import com.haduc.quicklibbooksmanagement.dto.LibraryDto;
import com.haduc.quicklibbooksmanagement.entity.Book;
import com.haduc.quicklibbooksmanagement.entity.Category;
import com.haduc.quicklibbooksmanagement.entity.Library;
import com.haduc.quicklibbooksmanagement.entity.LibraryBook;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-24T15:06:44+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class LibraryBookMapperImpl implements LibraryBookMapper {

    @Override
    public LibraryBookDto toLibraryBookDto(LibraryBook libraryBook) {
        if ( libraryBook == null ) {
            return null;
        }

        LibraryBookDto libraryBookDto = new LibraryBookDto();

        libraryBookDto.setId( libraryBook.getId() );
        libraryBookDto.setBook( bookToBookDto( libraryBook.getBook() ) );
        libraryBookDto.setLibrary( libraryToLibraryDto( libraryBook.getLibrary() ) );
        libraryBookDto.setStatus( libraryBook.getStatus() );
        libraryBookDto.setCode( libraryBook.getCode() );
        libraryBookDto.setQuantity( libraryBook.getQuantity() );
        libraryBookDto.setCreated_at( libraryBook.getCreated_at() );
        libraryBookDto.setUpdated_at( libraryBook.getUpdated_at() );

        return libraryBookDto;
    }

    @Override
    public LibraryBook toLibraryBook(LibraryBookDto libraryBookDto) {
        if ( libraryBookDto == null ) {
            return null;
        }

        LibraryBook libraryBook = new LibraryBook();

        libraryBook.setId( libraryBookDto.getId() );
        libraryBook.setBook( bookDtoToBook( libraryBookDto.getBook() ) );
        libraryBook.setLibrary( libraryDtoToLibrary( libraryBookDto.getLibrary() ) );
        libraryBook.setStatus( libraryBookDto.getStatus() );
        libraryBook.setCode( libraryBookDto.getCode() );
        libraryBook.setQuantity( libraryBookDto.getQuantity() );
        libraryBook.setCreated_at( libraryBookDto.getCreated_at() );
        libraryBook.setUpdated_at( libraryBookDto.getUpdated_at() );

        return libraryBook;
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

    protected BookDto bookToBookDto(Book book) {
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

    protected LibraryDto libraryToLibraryDto(Library library) {
        if ( library == null ) {
            return null;
        }

        LibraryDto libraryDto = new LibraryDto();

        libraryDto.setId( library.getId() );
        libraryDto.setName( library.getName() );
        libraryDto.setLocation( library.getLocation() );
        libraryDto.setCreated_at( library.getCreated_at() );
        libraryDto.setUpdated_at( library.getUpdated_at() );

        return libraryDto;
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

    protected Book bookDtoToBook(BookDto bookDto) {
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

    protected Library libraryDtoToLibrary(LibraryDto libraryDto) {
        if ( libraryDto == null ) {
            return null;
        }

        Library library = new Library();

        library.setId( libraryDto.getId() );
        library.setName( libraryDto.getName() );
        library.setLocation( libraryDto.getLocation() );
        library.setCreated_at( libraryDto.getCreated_at() );
        library.setUpdated_at( libraryDto.getUpdated_at() );

        return library;
    }
}
