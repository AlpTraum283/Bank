package com.netcracker.service;

import com.netcracker.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserEntityService {

    @Autowired
    ObjectRepository repository;

    public List<String> selectUserAndAccounts(Integer id){
        return  repository.selectUserByIdAndAccounts(id);
    }
}
