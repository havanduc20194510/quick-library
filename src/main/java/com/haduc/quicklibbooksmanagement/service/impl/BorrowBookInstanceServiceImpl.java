package com.haduc.quicklibbooksmanagement.service.impl;

import com.haduc.quicklibbooksmanagement.dto.BorrowBookInstanceDto;
import com.haduc.quicklibbooksmanagement.entity.*;
import com.haduc.quicklibbooksmanagement.mapper.BorrowBookInstanceMapper;
import com.haduc.quicklibbooksmanagement.repository.*;
import com.haduc.quicklibbooksmanagement.service.BorrowBookInstanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BorrowBookInstanceServiceImpl implements BorrowBookInstanceService {
    BorrowRequestRepository bookRequestRepository;

    BookRepository bookRepository;
    private LibraryBookRepository libraryBookRepository;
    private BorrowBookInstanceRepository borrowBookInstanceRepository;

    BorrowBookInstanceMapper borrowBookInstanceMapper;

    BorrowRequestRepository borrowRequestRepository;
    UserRepository userRepository;



    @Override
    public String create(Long userId, Long bookId, Long libraryId) {
        boolean hasRequestUnsent = false;
        BorrowBookInstanceDto borrowBookInstanceDto = new BorrowBookInstanceDto();
        LibraryBook libraryBook = libraryBookRepository.findByBookIdAndLibrary_Id(bookId,libraryId);
        Book book = libraryBook.getBook();
        Library library = libraryBook.getLibrary();
        Date currentDate = new Date();

        List<BorrowRequest> borrowRequests = bookRequestRepository.findByUserIdAndLibraryId(userId, library.getId());
        if (borrowRequests.size() > 0) {
            for (BorrowRequest request : borrowRequests) {
                if (request.getStatus().equals(BorrowStatus.UNSENT)) {
                    hasRequestUnsent = true;
                    borrowBookInstanceDto.setBorrowRequest(request);
                    borrowBookInstanceDto.setLibraryBook(libraryBook);
                    borrowBookInstanceDto.setCreatedAt(currentDate);
                    borrowBookInstanceDto.setUpdatedAt(currentDate);
                    if(borrowBookInstanceRepository.findByBorrowRequest_IdAndLibraryBook_Id(request.getId(),libraryBook.getId()) != null){
                        return "book already in request";
                    }
                    borrowBookInstanceRepository.save(borrowBookInstanceMapper.toBorrowBookInstance(borrowBookInstanceDto));
                    break;
                }
            }

        }else {
            BorrowRequest borrowRequest = new BorrowRequest();
            User user = userRepository.findById(userId).get();
            borrowRequest.setUser(user);
            borrowRequest.setLibrary(library);
            borrowRequest.setStatus(BorrowStatus.UNSENT);
            borrowRequest.setCreatedAt(currentDate);
            borrowRequest.setUpdatedAt(currentDate);
            borrowRequestRepository.save(borrowRequest);
            borrowBookInstanceDto.setBorrowRequest(borrowRequest);
            borrowBookInstanceDto.setLibraryBook(libraryBook);
            borrowBookInstanceDto.setCreatedAt(currentDate);
            borrowBookInstanceDto.setUpdatedAt(currentDate);
            if(borrowBookInstanceRepository.findByBorrowRequest_IdAndLibraryBook_Id(borrowRequest.getId(),libraryBook.getId()) != null){
                return "book already in request";
            }
            borrowBookInstanceRepository.save(borrowBookInstanceMapper.toBorrowBookInstance(borrowBookInstanceDto));
        }
        return "add book to request successfully";
    }

  /*  @Override
    public List<BorrowBookInstanceDto> getBorrowBookInstancesByBorrowRequestId(Long borrowRequestId) {
        List<BorrowBookInstance> borrowBookInstances = borrowBookInstanceRepository.findByBorrowRequestId(borrowRequestId);
        return borrowBookInstances.stream().map(
                borrowBookInstance -> borrowBookInstanceMapper.toBorrowBookInstanceDto(borrowBookInstance)
        ).collect(Collectors.toList());
    }*/
}
