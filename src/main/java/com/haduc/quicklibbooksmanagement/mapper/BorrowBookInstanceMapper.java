package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.BorrowBookInstanceDto;
import com.haduc.quicklibbooksmanagement.entity.BorrowBookInstance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {BorrowRequestMapper.class, LibraryBookMapper.class})
public interface BorrowBookInstanceMapper {

    BorrowBookInstanceMapper MAPPER = Mappers.getMapper(BorrowBookInstanceMapper.class);
    BorrowBookInstanceDto toBorrowBookInstanceDto(BorrowBookInstance borrowBookInstance);
    BorrowBookInstance toBorrowBookInstance(BorrowBookInstanceDto borrowBookInstanceDto);

}
