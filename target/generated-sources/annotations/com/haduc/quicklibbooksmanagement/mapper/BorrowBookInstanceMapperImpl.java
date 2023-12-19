package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.BorrowBookInstanceDto;
import com.haduc.quicklibbooksmanagement.entity.BorrowBookInstance;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-19T22:25:42+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class BorrowBookInstanceMapperImpl implements BorrowBookInstanceMapper {

    @Override
    public BorrowBookInstanceDto toBorrowBookInstanceDto(BorrowBookInstance borrowBookInstance) {
        if ( borrowBookInstance == null ) {
            return null;
        }

        BorrowBookInstanceDto borrowBookInstanceDto = new BorrowBookInstanceDto();

        borrowBookInstanceDto.setId( borrowBookInstance.getId() );
        borrowBookInstanceDto.setCreatedAt( borrowBookInstance.getCreatedAt() );
        borrowBookInstanceDto.setUpdatedAt( borrowBookInstance.getUpdatedAt() );
        borrowBookInstanceDto.setBorrowRequest( borrowBookInstance.getBorrowRequest() );
        borrowBookInstanceDto.setLibraryBook( borrowBookInstance.getLibraryBook() );

        return borrowBookInstanceDto;
    }

    @Override
    public BorrowBookInstance toBorrowBookInstance(BorrowBookInstanceDto borrowBookInstanceDto) {
        if ( borrowBookInstanceDto == null ) {
            return null;
        }

        BorrowBookInstance borrowBookInstance = new BorrowBookInstance();

        borrowBookInstance.setId( borrowBookInstanceDto.getId() );
        borrowBookInstance.setCreatedAt( borrowBookInstanceDto.getCreatedAt() );
        borrowBookInstance.setUpdatedAt( borrowBookInstanceDto.getUpdatedAt() );
        borrowBookInstance.setBorrowRequest( borrowBookInstanceDto.getBorrowRequest() );
        borrowBookInstance.setLibraryBook( borrowBookInstanceDto.getLibraryBook() );

        return borrowBookInstance;
    }
}
