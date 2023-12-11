package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.AuthorDto;
import com.haduc.quicklibbooksmanagement.entity.Author;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-11T12:49:12+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDto toAuthorDto(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorDto authorDto = new AuthorDto();

        authorDto.setId( author.getId() );
        authorDto.setName( author.getName() );
        authorDto.setDescription( author.getDescription() );
        authorDto.setCreated_at( author.getCreated_at() );
        authorDto.setUpdated_at( author.getUpdated_at() );

        return authorDto;
    }

    @Override
    public Author toAuthor(AuthorDto authorDto) {
        if ( authorDto == null ) {
            return null;
        }

        Author author = new Author();

        author.setId( authorDto.getId() );
        author.setName( authorDto.getName() );
        author.setDescription( authorDto.getDescription() );
        author.setCreated_at( authorDto.getCreated_at() );
        author.setUpdated_at( authorDto.getUpdated_at() );

        return author;
    }
}
