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

    public List<String> selectAccountsByUserId(Integer id){
        return  repository.selectAccountsByUserId(id);
    }

    public String selectUserById(Integer id){
        return repository.selectUserById(id);
    }
}
