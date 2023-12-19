package com.haduc.quicklibbooksmanagement.service.impl;

import com.haduc.quicklibbooksmanagement.dto.BorrowBookInstanceDto;
import com.haduc.quicklibbooksmanagement.service.BorrowBookInstanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BorrowBookInstanceServiceImpl implements BorrowBookInstanceService {
    @Override
    public BorrowBookInstanceDto create(BorrowBookInstanceDto borrowBookInstanceDto) {
        // kiem tra xem sach co con trong thu vien hay khong
        // kiem tra xem nguoi dung co the muon sach nay hay khong
        
        return null;
    }
}
