package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.BorrowRequestDto;
import com.haduc.quicklibbooksmanagement.entity.BorrowRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class, LibraryMapper.class})
public interface BorrowRequestMapper {
    BorrowRequestMapper MAPPER = Mappers.getMapper(BorrowRequestMapper.class);

    BorrowRequest toBorrowRequest(BorrowRequestDto borrowRequestDto);

    BorrowRequestDto toBorrowRequestDto(BorrowRequest borrowRequest);
}
