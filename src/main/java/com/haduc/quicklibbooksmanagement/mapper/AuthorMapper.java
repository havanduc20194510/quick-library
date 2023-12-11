package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.AuthorDto;
import com.haduc.quicklibbooksmanagement.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorMapper MAPPER = Mappers.getMapper(AuthorMapper.class);

    AuthorDto toAuthorDto(Author author);
    Author toAuthor(AuthorDto authorDto);
}
