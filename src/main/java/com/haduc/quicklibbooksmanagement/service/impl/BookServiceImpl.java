package com.haduc.quicklibbooksmanagement.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.haduc.quicklibbooksmanagement.dto.*;
import com.haduc.quicklibbooksmanagement.entity.Author;
import com.haduc.quicklibbooksmanagement.entity.AuthorBook;
import com.haduc.quicklibbooksmanagement.entity.Book;
import com.haduc.quicklibbooksmanagement.entity.Library;
import com.haduc.quicklibbooksmanagement.mapper.*;
import com.haduc.quicklibbooksmanagement.repository.*;
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
    private AuthorBookRepository authorBookRepository;

    private AuthorBookMapper authorBookMapper;

    private AuthorMapper authorMapper;

    private BookMapper bookMapper;

    private CategoryRepository categoryRepository;


    private Cloudinary cloudinary;

    private LibraryBookRepository libraryBooRepository;

    private LibraryMapper libraryMapper;



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
           /* BookDto bookDto = bookMapper.toBookDto(book);
            List<AuthorBookDto> authorBookDtos = book.getAuthorBooks().stream()
                    .map(authorBook -> authorBookMapper.toAuthorBookDto(authorBook))
                    .collect(Collectors.toList());
            List<LibraryBookDto> libraryBookDtos = book.getLibraryBooks().stream()
                    .map(libraryBook -> libraryBookMapper.toLibraryBookDto(libraryBook))
                    .collect(Collectors.toList());
            ResultDto resultDto = new ResultDto(bookDto, authorBookDtos, libraryBookDtos);*/
            BookDto bookDto = bookMapper.toBookDto(book);
            List<Author> authorList = book.getAuthorBooks().stream()
                    .map(authorBook -> authorBook.getAuthor())
                    .collect(Collectors.toList());
            List<AuthorDto> authorDtos = authorList.stream()
                    .map(author -> authorMapper.toAuthorDto(author))
                    .collect(Collectors.toList());
            List<Library> libraryList = book.getLibraryBooks().stream()
                    .map(libraryBook -> libraryBook.getLibrary())
                    .collect(Collectors.toList());
            List<LibraryDto> libraryDtos = libraryList.stream()
                    .map(library -> libraryMapper.toLibraryDto(library))
                    .collect(Collectors.toList());
            ResultDto resultDto = new ResultDto(bookDto, authorDtos, libraryDtos);
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
    public List<ResultDto> search(String title, String authorName, Integer publishYear, String libraryName, Long category) {
        List<Book> bookList = bookRepository.findAll();
        List<Book> bookListResult = new ArrayList<>();
        for(Book book : bookList){
            if(title != null && !title.isEmpty()){
                if(!book.getTitle().toLowerCase().contains(title.toLowerCase())){
                    continue;
                }
            }
            if(authorName != null && !authorName.isEmpty()){
                String authorNames = book.getAuthorBooks().stream()
                        .map(authorBook -> authorBook.getAuthor().getName())
                        .collect(Collectors.joining());
                if(!(authorNames.toLowerCase().contains(authorName.toLowerCase()))){
                    continue;
                }
            }
            if(publishYear!=null){
                if(book.getPublish_year() != publishYear.intValue()){
                    continue;
                }
            }
            if(libraryName != null && !libraryName.isEmpty()){
                String libraryNames = book.getLibraryBooks().stream()
                        .map(libraryBook -> libraryBook.getLibrary().getName())
                        .collect(Collectors.joining());
                if(!(libraryNames.toLowerCase().contains(libraryName.toLowerCase()))){
                    continue;
                }
            }
            if(category != null && category > 0){
                Long categoryId = book.getCategory().getId();
                if(!categoryId.equals(category)){
                    continue;
                }
            }
            bookListResult.add(book);
        }
        List<ResultDto> resultDtoList = new ArrayList<>();
        for(Book book : bookListResult){
            BookDto bookDto = bookMapper.toBookDto(book);
            /*List<AuthorDto> authorDtos = book.getAuthorBooks().stream()
                    .map(authorBook -> authorBookMapper.toAuthorBookDto(authorBook))
                    .collect(Collectors.toList());
            List<LibraryBookDto> libraryBookDtos = book.getLibraryBooks().stream()
                    .map(libraryBook -> libraryBookMapper.toLibraryBookDto(libraryBook))
                    .collect(Collectors.toList());*/
            List<AuthorDto> authorDtos = book.getAuthorBooks().stream()
                    .map(authorBook -> authorBook.getAuthor())
                    .map(author -> authorMapper.toAuthorDto(author))
                    .collect(Collectors.toList());
            List<LibraryDto> libraryDtos = book.getLibraryBooks().stream()
                    .map(libraryBook -> libraryBook.getLibrary())
                    .map(library -> libraryMapper.toLibraryDto(library))
                    .collect(Collectors.toList());

            ResultDto resultDto = new ResultDto(bookDto, authorDtos, libraryDtos);
            resultDtoList.add(resultDto);
        }
        return resultDtoList;
    }
}
