package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.LibraryBookDto;
import com.haduc.quicklibbooksmanagement.entity.LibraryBook;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LibraryBookMapper {
    LibraryBookMapper MAPPER = Mappers.getMapper(LibraryBookMapper.class);
    LibraryBookDto toLibraryBookDto(LibraryBook libraryBook);

    LibraryBook toLibraryBook(LibraryBookDto libraryBookDto);
}
