package com.haduc.quicklibbooksmanagement.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.haduc.quicklibbooksmanagement.dto.*;
import com.haduc.quicklibbooksmanagement.entity.*;
import com.haduc.quicklibbooksmanagement.mapper.*;
import com.haduc.quicklibbooksmanagement.repository.*;
import com.haduc.quicklibbooksmanagement.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private AuthorMapper authorMapper;

    private BookMapper bookMapper;

    private CategoryRepository categoryRepository;


    private Cloudinary cloudinary;

    private LibraryBookRepository libraryBooRepository;

    private LibraryMapper libraryMapper;
    private final AuthorRepository authorRepository;


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

        List<Book> ortherBooks = bookRepository.findAll().stream()
                .filter(book1 -> {
                    List<Long> authorIds1 = book1.getAuthorBooks().stream()
                            .map(authorBook -> authorBook.getAuthor().getId())
                            .collect(Collectors.toList());
                    for(Long authorId : authorIds){
                        if(authorIds1.contains(authorId) && !book1.getId().equals(book.getId())){
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());

        List<BookDto> ortherBookDtos = ortherBooks.stream()
                .map(book1 -> bookMapper.toBookDto(book1))
                .collect(Collectors.toList());

        BookInstanceDto bookInstanceDto = new BookInstanceDto(bookDto, authorDtos, libraryDto, ortherBookDtos);
        return bookInstanceDto;
    }

    @Override
    public Page<ResultConvertDto> getAllConvert(int page, int size) {
        List<Book> bookList = bookRepository.findAll();
        List<ResultConvertDto> resultDtoList = new ArrayList<>();
        for(Book book : bookList){
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

            for (LibraryDto libraryDto : libraryDtos) {
                ResultConvertDto resultConvertDto = new ResultConvertDto(bookDto, authorDtos, libraryDto);
                resultDtoList.add(resultConvertDto);
            }
        }
        return new PageImpl<>(resultDtoList, PageRequest.of(page-1, size), resultDtoList.size());
    }

    @Override
    public Page<ResultConvertDto> searchByParam(String title, String authorName, Integer publishYear, String libraryName, Long categoryId, int page, int size) {
        List<Book> bookList = bookRepository.findAll();
        List<Book> bookListResult = new ArrayList<>();
        List<LibraryBook> libraryBookResult = new ArrayList<>();
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
                for(LibraryBook libraryBook : book.getLibraryBooks()) {
                    if(!libraryBook.getLibrary().getName().toLowerCase().contains(libraryName.toLowerCase())) {
                        //library_name = libraryBook.getLibrary().getName();
                        continue;
                    }
                    libraryBookResult.add(libraryBook);
                }
                if(libraryBookResult.isEmpty()){
                    continue;
                }else {
                    book.setLibraryBooks(libraryBookResult);
                }
            }

            if(categoryId != null && categoryId > 0){
                Long category = book.getCategory().getId();
                if(!category.equals(categoryId)){
                    continue;
                }
            }
            bookListResult.add(book);
        }
        List<ResultConvertDto> resultDtoList = new ArrayList<>();
        for(Book book : bookListResult) {
            List<LibraryBook> libraryBooks = book.getLibraryBooks();
            List<AuthorDto> authorDtos = book.getAuthorBooks().stream()
                    .map(authorBook -> authorBook.getAuthor())
                    .map(author -> authorMapper.toAuthorDto(author))
                    .collect(Collectors.toList());
            ResultConvertDto resultConvertDto = new ResultConvertDto();
            for (LibraryBook libraryBook : libraryBooks) {
                if (libraryBook.getBook().getId() == book.getId()) {
                    BookDto bookDto = bookMapper.toBookDto(book);
                    LibraryDto libraryDto = libraryMapper.toLibraryDto(libraryBook.getLibrary());
                    resultConvertDto = new ResultConvertDto(bookDto, authorDtos, libraryDto);
                    resultDtoList.add(resultConvertDto);
                }
            }
        }
        return new PageImpl<>(resultDtoList, PageRequest.of(page-1, size), resultDtoList.size());
    }
    @Override
    public List<ResultDto> search(String title, String authorName, Integer publishYear, String libraryName, Long categoryId) {
        List<Book> bookList = bookRepository.findAll();
        List<Book> bookListResult = new ArrayList<>();
        List<LibraryBook> libraryBookResult = new ArrayList<>();
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
                for(LibraryBook libraryBook : book.getLibraryBooks()) {
                    if(!libraryBook.getLibrary().getName().toLowerCase().contains(libraryName.toLowerCase())) {
                        //library_name = libraryBook.getLibrary().getName();
                        continue;
                    }
                    if(libraryBook.getLibrary() != null) {
                        libraryBookResult.add(libraryBook);
                    }
                }
                if(libraryBookResult.isEmpty()){
                    continue;
                }else {
                    book.setLibraryBooks(libraryBookResult);
                }
            }

            if(categoryId != null && categoryId > 0){
                Long category = book.getCategory().getId();
                if(!category.equals(categoryId)){
                    continue;
                }
            }
            bookListResult.add(book);
        }
        List<ResultDto> resultDtoList = new ArrayList<>();

        for(Book book : bookListResult){
            BookDto bookDto = bookMapper.toBookDto(book);
            List<AuthorDto> authorDtos = book.getAuthorBooks().stream()
                    .map(authorBook -> authorBook.getAuthor())
                    .map(author -> authorMapper.toAuthorDto(author))
                    .collect(Collectors.toList());
            List<LibraryBook> libraryBooks = book.getLibraryBooks();
            List<LibraryDto> libraryDtos = new ArrayList<>();
            for (LibraryBook libraryBook : libraryBooks) {
                if(libraryBook.getBook().getId() == book.getId()){
                    libraryDtos.add(libraryMapper.toLibraryDto(libraryBook.getLibrary()));
                }
            }
            ResultDto resultDto = new ResultDto(bookDto, authorDtos, libraryDtos);
            resultDtoList.add(resultDto);
        }
        // bỏ đi libaryDtos null trong resultDtoList
        resultDtoList = resultDtoList.stream()
                .filter(resultDto -> resultDto.getLibrarys().size() > 0)
                .collect(Collectors.toList());
        return resultDtoList;
    }

    @Override
    public Page<BookDetail> getAllBook(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<BookInfoDto> bookInfoDtoList = bookRepository.getBooksAndLibraries(pageable);
        List<BookDetail> bookDetailList = new ArrayList<>();
        for(BookInfoDto bookInfoDto : bookInfoDtoList){
            Category category = categoryRepository.findById(bookInfoDto.getParent_category_id()).orElse(null);
            String categoryName = category != null ? category.getName(): "";

            BookDetail bookDetail = new BookDetail();
            bookDetail.setId(bookInfoDto.getId());
            bookDetail.setTitle(bookInfoDto.getTitle());
            bookDetail.setDescription(bookInfoDto.getDescription());
            bookDetail.setQuantity(bookInfoDto.getQuantity());
            bookDetail.setCover_image_url(bookInfoDto.getCover_image_url());
            bookDetail.setPublish_year(bookInfoDto.getPublish_year());
            bookDetail.setPublisher(bookInfoDto.getPublisher());
            bookDetail.setLanguage(bookInfoDto.getLanguage());
            bookDetail.setPage_number(bookInfoDto.getPage_number());
            bookDetail.setCreated_at(bookInfoDto.getCreated_at());
            bookDetail.setUpdated_at(bookInfoDto.getUpdated_at());
            bookDetail.setCategory_id(bookInfoDto.getCategory_id());
            bookDetail.setCategory_name(bookInfoDto.getCategory_name());
            bookDetail.setParent_category_id(bookInfoDto.getParent_category_id());
            bookDetail.setParent_category_name(categoryName);
            bookDetail.setLibrary_id(bookInfoDto.getLibrary_id());
            bookDetail.setLibrary_name(bookInfoDto.getLibrary_name());
            bookDetail.setLocation(bookInfoDto.getLocation());
            bookDetail.setCode(bookInfoDto.getCode());
            bookDetail.setStatus(bookInfoDto.getStatus());
            bookDetailList.add(bookDetail);
        }
        return new PageImpl<>(bookDetailList, pageable, bookInfoDtoList.getTotalElements());
    }
}
