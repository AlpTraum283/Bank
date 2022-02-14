package com.netcracker.service;

import com.netcracker.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AccountEntityService {

    @Autowired
    ObjectRepository repository;

    public Map<String, String> selectAccountById(Integer id) {
        return repository.selectAccountById(id);
    }

    public String returnAccountById(Integer id) {
        return repository.returnAccountById(id);
    }

    public  List<Map<String,String>> selectOperationsByAccountId(Integer id, Date startDate, Date endDate) {
        return repository.selectOperationsByAccountId(id, startDate, endDate);
    }
}
