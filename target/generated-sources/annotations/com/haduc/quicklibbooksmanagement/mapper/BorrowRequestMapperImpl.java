package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.BorrowRequestDto;
import com.haduc.quicklibbooksmanagement.entity.BorrowBookInstance;
import com.haduc.quicklibbooksmanagement.entity.BorrowRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-20T11:45:15+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class BorrowRequestMapperImpl implements BorrowRequestMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LibraryMapper libraryMapper;

    @Override
    public BorrowRequest toBorrowRequest(BorrowRequestDto borrowRequestDto) {
        if ( borrowRequestDto == null ) {
            return null;
        }

        BorrowRequest borrowRequest = new BorrowRequest();

        borrowRequest.setId( borrowRequestDto.getId() );
        borrowRequest.setUser( userMapper.toUser( borrowRequestDto.getUser() ) );
        borrowRequest.setStatus( borrowRequestDto.getStatus() );
        borrowRequest.setLibrary( libraryMapper.toLibrary( borrowRequestDto.getLibrary() ) );
        borrowRequest.setBorrowDate( borrowRequestDto.getBorrowDate() );
        borrowRequest.setRequestDueDate( borrowRequestDto.getRequestDueDate() );
        borrowRequest.setReturnDate( borrowRequestDto.getReturnDate() );
        borrowRequest.setCode( borrowRequestDto.getCode() );
        borrowRequest.setCreatedAt( borrowRequestDto.getCreatedAt() );
        borrowRequest.setUpdatedAt( borrowRequestDto.getUpdatedAt() );
        List<BorrowBookInstance> list = borrowRequestDto.getBorrowBookInstances();
        if ( list != null ) {
            borrowRequest.setBorrowBookInstances( new ArrayList<BorrowBookInstance>( list ) );
        }

        return borrowRequest;
    }

    @Override
    public BorrowRequestDto toBorrowRequestDto(BorrowRequest borrowRequest) {
        if ( borrowRequest == null ) {
            return null;
        }

        BorrowRequestDto borrowRequestDto = new BorrowRequestDto();

        borrowRequestDto.setId( borrowRequest.getId() );
        borrowRequestDto.setUser( userMapper.toUserDto( borrowRequest.getUser() ) );
        borrowRequestDto.setStatus( borrowRequest.getStatus() );
        borrowRequestDto.setLibrary( libraryMapper.toLibraryDto( borrowRequest.getLibrary() ) );
        borrowRequestDto.setBorrowDate( borrowRequest.getBorrowDate() );
        borrowRequestDto.setRequestDueDate( borrowRequest.getRequestDueDate() );
        borrowRequestDto.setReturnDate( borrowRequest.getReturnDate() );
        borrowRequestDto.setCode( borrowRequest.getCode() );
        borrowRequestDto.setCreatedAt( borrowRequest.getCreatedAt() );
        borrowRequestDto.setUpdatedAt( borrowRequest.getUpdatedAt() );
        List<BorrowBookInstance> list = borrowRequest.getBorrowBookInstances();
        if ( list != null ) {
            borrowRequestDto.setBorrowBookInstances( new ArrayList<BorrowBookInstance>( list ) );
        }

        return borrowRequestDto;
    }
}
