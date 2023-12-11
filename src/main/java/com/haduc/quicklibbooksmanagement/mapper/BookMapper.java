package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.BookDto;
import com.haduc.quicklibbooksmanagement.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {LibraryBookMapper.class, AuthorBookMapper.class})
public interface BookMapper {
    BookMapper MAPPER = Mappers.getMapper(BookMapper.class);
    Book toBook(BookDto bookDto);
    BookDto toBookDto(Book book);
}
