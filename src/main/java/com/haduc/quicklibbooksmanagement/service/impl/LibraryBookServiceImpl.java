package com.haduc.quicklibbooksmanagement.service.impl;

import com.haduc.quicklibbooksmanagement.dto.LibraryBookDto;
import com.haduc.quicklibbooksmanagement.entity.LibraryBook;
import com.haduc.quicklibbooksmanagement.mapper.LibraryBookMapper;
import com.haduc.quicklibbooksmanagement.repository.LibraryBookRepository;
import com.haduc.quicklibbooksmanagement.service.LibraryBookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LibraryBookServiceImpl implements LibraryBookService {

    private LibraryBookRepository libraryBookRepository;

    private LibraryBookMapper libraryBookMapper;

    @Override
    public LibraryBookDto insertLibraryBook(LibraryBookDto libraryBookDto) {
        Date currentTime = new Date();
        libraryBookDto.setCreated_at(currentTime);
        libraryBookDto.setUpdated_at(currentTime);
        LibraryBook libraryBook = libraryBookMapper.toLibraryBook(libraryBookDto);
        LibraryBook libraryBookSaved = libraryBookRepository.save(libraryBook);
        LibraryBookDto libraryBookDtoSaved = libraryBookMapper.toLibraryBookDto(libraryBookSaved);
        return libraryBookDtoSaved;
    }

    @Override
    public List<LibraryBookDto> getAll() {
        List<LibraryBook> libraryBookList = libraryBookRepository.findAll();
        return libraryBookList.stream()
                .map(libraryBook -> libraryBookMapper.toLibraryBookDto(libraryBook))
                .collect(Collectors.toList());
    }
}
