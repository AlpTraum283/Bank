package com.netcracker.service;

import com.netcracker.dto.ObjectDto;
import com.netcracker.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserEntityService {

    @Autowired
    ObjectRepository repository;

    public ObjectDto getByObjIdAndType(Integer id, String type){
        return  repository.getByObjIdAndType(id, type);
    }

//    public String selectUserById(Integer id){
//        return repository.selectUserById(id);
//    }
}
