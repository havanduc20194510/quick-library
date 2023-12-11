package com.haduc.quicklibbooksmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    private BookDto bookDto;
    private List<AuthorBookDto> authorBookDtos;
    private List<LibraryBookDto> libraryBookDtos;
}
