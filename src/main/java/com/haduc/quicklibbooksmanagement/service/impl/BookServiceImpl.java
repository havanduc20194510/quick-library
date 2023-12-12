package com.haduc.quicklibbooksmanagement.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.haduc.quicklibbooksmanagement.dto.*;
import com.haduc.quicklibbooksmanagement.entity.Author;
import com.haduc.quicklibbooksmanagement.entity.Book;
import com.haduc.quicklibbooksmanagement.entity.Library;
import com.haduc.quicklibbooksmanagement.mapper.*;
import com.haduc.quicklibbooksmanagement.repository.BookRepository;
import com.haduc.quicklibbooksmanagement.repository.CategoryRepository;
import com.haduc.quicklibbooksmanagement.repository.LibraryBookRepository;
import com.haduc.quicklibbooksmanagement.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    //private ResultMapper resultMapper;
    private BookMapper bookMapper;
    private CategoryRepository categoryRepository;

    private AuthorBookMapper authorBookMapper;

    private LibraryBookRepository libraryBooRepository;

    private LibraryBookMapper libraryBookMapper;

    private AuthorMapper authorMapper;

    private LibraryMapper libraryMapper;
    private Cloudinary cloudinary;
    @Override
    public BookDto insertBook(BookDto bookDto, MultipartFile image) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        bookDto.setCover_image_url(uploadResult.get("url").toString());
        Long parentCategoryId = bookDto.getCategory().getParent_category_id();
        if(parentCategoryId != null){
            List<Long> categoryIds = categoryRepository.findAll().stream()
                    .map(category -> category.getId())
                    .collect(Collectors.toList());
            if(!categoryIds.contains(parentCategoryId)){
                parentCategoryId = -1L;
            }
        }
        Date currentTime = new Date();
        bookDto.setCreated_at(currentTime);
        bookDto.setUpdated_at(currentTime);
        Book book = bookMapper.toBook(bookDto);
        Book bookSaved = bookRepository.save(book);
        BookDto bookDtoSaved = bookMapper.toBookDto(bookSaved);
        return bookDtoSaved;
    }

    @Override
    public List<ResultDto> getAll() {
        List<Book> bookList = bookRepository.findAll();
        List<ResultDto> resultDtoList = new ArrayList<>();
        for(Book book : bookList){
            BookDto bookDto = bookMapper.toBookDto(book);
            List<AuthorBookDto> authorBookDtos = book.getAuthorBooks().stream()
                    .map(authorBook -> authorBookMapper.toAuthorBookDto(authorBook))
                    .collect(Collectors.toList());
            List<LibraryBookDto> libraryBookDtos = book.getLibraryBooks().stream()
                    .map(libraryBook -> libraryBookMapper.toLibraryBookDto(libraryBook))
                    .collect(Collectors.toList());
            ResultDto resultDto = new ResultDto(bookDto, authorBookDtos, libraryBookDtos);
            resultDtoList.add(resultDto);
        }
        return resultDtoList;
    }

    @Override
    public BookInstanceDto getById(Long id) {
        Book book = libraryBooRepository.findById(id).get().getBook();
        BookDto bookDto = bookMapper.toBookDto(book);
        List<Long> authorIds = book.getAuthorBooks().stream()
                .map(authorBook -> authorBook.getAuthor().getId())
                .collect(Collectors.toList());
        Library library = libraryBooRepository.findById(id).get().getLibrary();
        LibraryDto libraryDto = libraryMapper.toLibraryDto(library);
        List<Author> authorList = book.getAuthorBooks().stream()
                .map(authorBook -> authorBook.getAuthor())
                .collect(Collectors.toList());
        List<AuthorDto> authorDtos = authorList.stream()
                .map(author -> authorMapper.toAuthorDto(author))
                .collect(Collectors.toList());
      /*  List<AuthorBookDto> authorBookDtos = book.getAuthorBooks().stream()
                .map(authorBook -> authorBookMapper.toAuthorBookDto(authorBook))
                .collect(Collectors.toList());
        List<LibraryBookDto> libraryBookDtos = book.getLibraryBooks().stream()
                .map(libraryBook -> libraryBookMapper.toLibraryBookDto(libraryBook))
                .collect(Collectors.toList());*/
        BookInstanceDto bookInstanceDto = new BookInstanceDto(bookDto, authorDtos, libraryDto);
        return bookInstanceDto;
    }

   /* @Override
    public List<Book> findByTitleContainingIgnoreCase(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Book> findByAuthorBooksAuthorNameIgnoreCase(String authorName) {
        return bookRepository.findByAuthorBooksAuthorNameIgnoreCase(authorName);
    }

    @Override
    public List<Book> findByPublishYear(Integer publishYear) {
        return bookRepository.findByPublishYear(publishYear);
    }

    @Override
    public List<Book> findByLibraryBooksLibraryNameIgnoreCase(String libraryName) {
        return bookRepository.findByLibraryBooksLibraryNameIgnoreCase(libraryName);
    }

    @Override
    public List<Book> findByCategoryIn(List<Category> categories) {
        return bookRepository.findByCategoryIn(categories);
    }
*/
    @Override
    public List<ResultDto> search(String title, String authorName, Integer publishYear, String libraryName, String category) {
        List<Book> bookList = bookRepository.findAll();
        List<Book> bookListResult = new ArrayList<>();
        for(Book book : bookList){
            if(title != null && !title.isEmpty()){
                if(!book.getTitle().toLowerCase().contains(title.toLowerCase())){
                    continue;
                }
            }
            if(authorName != null && !authorName.isEmpty()){
                if(!(book.getAuthorBooks().stream()
                        .map(authorBook -> authorBook.getAuthor().getName().toLowerCase())
                        .collect(Collectors.toList())
                        .contains(authorName.toLowerCase()))){
                    continue;
                }
            }
            if(publishYear!=null){
                if(book.getPublish_year() != publishYear.intValue()){
                    continue;
                }
            }
            if(libraryName != null && !libraryName.isEmpty()){
                if(!book.getLibraryBooks().stream()
                        .map(libraryBook -> libraryBook.getLibrary().getName())
                        .collect(Collectors.toList())
                        .contains(libraryName)){
                    continue;
                }
            }
            if(category != null && !category.isEmpty()){
                String categoryName = book.getCategory().getName();
                if(!categoryName.toLowerCase().contains(category.toLowerCase())){
                    continue;
                }
            }
            bookListResult.add(book);
        }
        List<ResultDto> resultDtoList = new ArrayList<>();
        for(Book book : bookListResult){
            BookDto bookDto = bookMapper.toBookDto(book);
            List<AuthorBookDto> authorBookDtos = book.getAuthorBooks().stream()
                    .map(authorBook -> authorBookMapper.toAuthorBookDto(authorBook))
                    .collect(Collectors.toList());
            List<LibraryBookDto> libraryBookDtos = book.getLibraryBooks().stream()
                    .map(libraryBook -> libraryBookMapper.toLibraryBookDto(libraryBook))
                    .collect(Collectors.toList());
            ResultDto resultDto = new ResultDto(bookDto, authorBookDtos, libraryBookDtos);
            resultDtoList.add(resultDto);
        }
        return resultDtoList;
    }
}
