package com.netcracker.service;

import com.netcracker.model.dto.database.ObjectDto;
import com.netcracker.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserEntityService {

    @Autowired
    ObjectRepository repository;

    public ObjectDto getByObjIdAndType(Integer id, String type) {
        return repository.getByObjIdAndType(id, type);
    }

    public List<ObjectDto> getByOwnerAndType(Integer owner, String type) {
        return repository.getByOwnerAndType(owner, type);
    }
}
