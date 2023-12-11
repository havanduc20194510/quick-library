package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.LibraryDto;
import com.haduc.quicklibbooksmanagement.entity.Library;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LibraryMapper {
    LibraryMapper MAPPER = Mappers.getMapper(LibraryMapper.class);
    LibraryDto toLibraryDto(Library library);
    Library toLibrary(LibraryDto libraryDto);
}
