package com.haduc.quicklibbooksmanagement.service.impl;

import com.haduc.quicklibbooksmanagement.dto.LibraryDto;
import com.haduc.quicklibbooksmanagement.entity.Library;
import com.haduc.quicklibbooksmanagement.mapper.LibraryMapper;
import com.haduc.quicklibbooksmanagement.repository.LibraryRepository;
import com.haduc.quicklibbooksmanagement.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private LibraryRepository libraryRepository;
    private LibraryMapper libraryMapper;
    @Override
    public LibraryDto insertLibrary(LibraryDto libraryDto) {
        Date currentTime = new Date();
        libraryDto.setCreated_at(currentTime);
        libraryDto.setUpdated_at(currentTime);
        Library library = libraryMapper.toLibrary(libraryDto);
        Library librarySaved = libraryRepository.save(library);
        LibraryDto libraryDtoSaved = libraryMapper.toLibraryDto(librarySaved);
        return libraryDtoSaved;
    }

    @Override
    public List<LibraryDto> getAll() {
        List<Library> libraryList = libraryRepository.findAll();
        return libraryList.stream()
                .map(library -> libraryMapper.toLibraryDto(library))
                .collect(java.util.stream.Collectors.toList());
    }
}
