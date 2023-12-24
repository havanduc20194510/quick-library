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
    public Page<ResultDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Book> bookList = bookRepository.findAll(pageable);
        List<ResultDto> resultDtoList = new ArrayList<>();
        for(Book book : bookList){
            BookDto bookDto = bookMapper.toBookDto(book);
            Category category = categoryRepository.findById(book.getCategory().getParent_category_id()).orElse(null);
            String category_name = category != null ? category.getName(): "";
            bookDto.getCategory().setParent_category_name(category_name);
            int totalQuantity = book.getLibraryBooks().stream()
                    .mapToInt(libraryBook -> libraryBook.getQuantity())
                    .sum();
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

            // lay ra so luong sach cua tung thu vien
            List<LibraryBookInfoDto> libraryBookInfoDtos = new ArrayList<>();
            for(LibraryDto libraryDto : libraryDtos){
                LibraryBookInfoDto libraryBookInfoDto = new LibraryBookInfoDto();
                libraryBookInfoDto.setLibrary(libraryDto);
                int quantity = book.getLibraryBooks().stream()
                        .filter(libraryBook -> libraryBook.getLibrary().getId().equals(libraryDto.getId()))
                        .mapToInt(libraryBook -> libraryBook.getQuantity())
                        .sum();

                libraryBookInfoDto.setQuantity(quantity);
                libraryBookInfoDtos.add(libraryBookInfoDto);
            }
            ResultDto resultDto = new ResultDto(bookDto, authorDtos, libraryBookInfoDtos,totalQuantity);
            resultDtoList.add(resultDto);
        }
        resultDtoList.sort((o1, o2) -> o1.getBook().getTitle().compareTo(o2.getBook().getTitle()));
        return new PageImpl<>(resultDtoList, pageable, bookList.getTotalElements());
    }

    @Override
    public BookInstanceDto getById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
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
        // lay ra so luong sach cua tung thu vien
        List<LibraryBookInfoDto> libraryBookInfoDtos = new ArrayList<>();
        for(LibraryDto libraryDto : libraryDtos){
            LibraryBookInfoDto libraryBookInfoDto = new LibraryBookInfoDto();
            libraryBookInfoDto.setLibrary(libraryDto);
            int quantity = book.getLibraryBooks().stream()
                    .filter(libraryBook -> libraryBook.getLibrary().getId().equals(libraryDto.getId()))
                    .mapToInt(libraryBook -> libraryBook.getQuantity())
                    .sum();
            libraryBookInfoDto.setQuantity(quantity);
            libraryBookInfoDtos.add(libraryBookInfoDto);
        }
        // lay ra nhung cuon sach khac cua cung tac gia voi book
        List<Book> otherBooks = bookRepository.findAll().stream()
                .filter(book1 -> {
                    List<Long> authorIds1 = book1.getAuthorBooks().stream()
                            .map(authorBook -> authorBook.getAuthor().getId())
                            .collect(Collectors.toList());
                    for(Long authorId : authorDtos.stream().map(authorDto -> authorDto.getId()).collect(Collectors.toList())){
                        if(authorIds1.contains(authorId) && !book1.getId().equals(book.getId())){
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
        List<BookDto> otherBookDtos = otherBooks.stream()
                .map(book1 -> bookMapper.toBookDto(book1))
                .collect(Collectors.toList());

        BookInstanceDto bookInstanceDto = new BookInstanceDto(bookDto, authorDtos, libraryBookInfoDtos,otherBookDtos);
        return bookInstanceDto;
    }

    public Page<ResultDto> search(String title, String authorName, Integer publishYear, String libraryName, Long categoryId, int page, int size) {
        List<ResultDto> resultDtoList = new ArrayList<>();
        Page<Book> books = new PageImpl<>(new ArrayList<>());
        if(title == null && authorName == null && publishYear == null && libraryName == null && categoryId == null){
            return getAll(page, size);
        }
        if(categoryId != null && categoryId > 0){
            if(title == null && authorName == null && publishYear == null && libraryName == null){
                books = bookRepository.findByCategoryIdOrParentCategoryId(categoryId, PageRequest.of(page-1, size));
            }

            if(title != null && !title.isEmpty()){
                books = findByTitleAndCategoryId(title, categoryId, page, size);
            }

            if(publishYear!= null){
                int publishYearInt = publishYear.intValue();
                books = bookRepository.findByPublishYearAndAndCategoryId(publishYearInt, categoryId, PageRequest.of(page-1, size));
            }

            if(libraryName != null && !libraryName.isEmpty()){
                books = bookRepository.findByCategoryIdAndLibraryName(categoryId, libraryName, PageRequest.of(page-1, size));
            }

            if(authorName != null && !authorName.isEmpty()){
                books = bookRepository.findByCategoryIdAndAuthorName(categoryId, authorName, PageRequest.of(page-1, size));
            }
        }else{
            if(title != null && !title.isEmpty()){
                books = bookRepository.findByTitleContainsIgnoreCase(title, PageRequest.of(page-1, size));
            }

            if(publishYear!= null){
                int publishYearInt = publishYear.intValue();
                books = bookRepository.findByPublishYear(publishYearInt, PageRequest.of(page-1, size));
            }

            if(authorName != null && !authorName.isEmpty()) {
                books = bookRepository.findByAuthorName(authorName, PageRequest.of(page-1, size));
            }

            if(libraryName != null && !libraryName.isEmpty()) {
                books = bookRepository.findByLibraryName(libraryName, PageRequest.of(page-1, size));
            }


        }
        for (Book book : books) {
            BookDto bookDto = bookMapper.toBookDto(book);
            Category category = categoryRepository.findById(book.getCategory().getParent_category_id()).orElse(null);
            String category_name = category != null ? category.getName(): "";
            bookDto.getCategory().setParent_category_name(category_name);
            int totalQuantity = book.getLibraryBooks().stream()
                    .mapToInt(libraryBook -> libraryBook.getQuantity())
                    .sum();
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
            // lay ra so luong sach cua tung thu vien
            List<LibraryBookInfoDto> libraryBookInfoDtos = new ArrayList<>();
            for(LibraryDto libraryDto : libraryDtos){
                LibraryBookInfoDto libraryBookInfoDto = new LibraryBookInfoDto();
                libraryBookInfoDto.setLibrary(libraryDto);
                int quantity = book.getLibraryBooks().stream()
                        .filter(libraryBook -> libraryBook.getLibrary().getId().equals(libraryDto.getId()))
                        .mapToInt(libraryBook -> libraryBook.getQuantity())
                        .sum();
                libraryBookInfoDto.setQuantity(quantity);
                libraryBookInfoDtos.add(libraryBookInfoDto);
            }
            ResultDto resultDto = new ResultDto(bookDto, authorDtos, libraryBookInfoDtos,totalQuantity);
            resultDtoList.add(resultDto);
        }
        return new PageImpl<>(resultDtoList, PageRequest.of(page-1, size), books.getTotalElements());
    }

    @Override
    public Page<Book> findByTitleAndCategoryId(String title, Long categoryId, int page, int size) {
        return bookRepository.findByTitleContainsIgnoreCaseAndCategoryId(title, categoryId, PageRequest.of(page-1, size));
    }

}
