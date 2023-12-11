package com.haduc.quicklibbooksmanagement.mapper;


import com.haduc.quicklibbooksmanagement.dto.AuthorBookDto;
import com.haduc.quicklibbooksmanagement.entity.AuthorBook;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthorBookMapper {
    AuthorBookMapper MAPPER = Mappers.getMapper(AuthorBookMapper.class);

    AuthorBook toAuthorBook(AuthorBookDto authorBookDto);
    AuthorBookDto toAuthorBookDto(AuthorBook authorBook);
}
