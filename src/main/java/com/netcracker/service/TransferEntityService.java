package com.netcracker.service;

import com.netcracker.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class TransferEntityService {

    @Autowired
    ObjectRepository repository;

//    public Integer selectBalanceByAccountId(Integer id) {
//
//        return repository.selectBalanceByAccountId(id);
//    }
}
