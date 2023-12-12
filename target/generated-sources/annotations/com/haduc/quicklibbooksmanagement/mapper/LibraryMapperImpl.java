package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.LibraryDto;
import com.haduc.quicklibbooksmanagement.entity.Library;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-12T11:50:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class LibraryMapperImpl implements LibraryMapper {

    @Override
    public LibraryDto toLibraryDto(Library library) {
        if ( library == null ) {
            return null;
        }

        LibraryDto libraryDto = new LibraryDto();

        libraryDto.setId( library.getId() );
        libraryDto.setName( library.getName() );
        libraryDto.setLocation( library.getLocation() );
        libraryDto.setCreated_at( library.getCreated_at() );
        libraryDto.setUpdated_at( library.getUpdated_at() );

        return libraryDto;
    }

    @Override
    public Library toLibrary(LibraryDto libraryDto) {
        if ( libraryDto == null ) {
            return null;
        }

        Library library = new Library();

        library.setId( libraryDto.getId() );
        library.setName( libraryDto.getName() );
        library.setLocation( libraryDto.getLocation() );
        library.setCreated_at( libraryDto.getCreated_at() );
        library.setUpdated_at( libraryDto.getUpdated_at() );

        return library;
    }
}
