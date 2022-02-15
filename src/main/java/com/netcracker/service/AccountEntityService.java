package com.netcracker.service;

import com.netcracker.dto.ObjectDto;
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

//      Возвращает обьект, основываясь на Id и Type обьекта
    public ObjectDto getByObjIdAndType(Integer id, String type) {
        return repository.getByObjIdAndType(id, type);
    }
//      Возвращает список обьектов на основе id владельца и type искомого обьекта
    public List<ObjectDto> getByOwnerAndType(Integer owner, String type) {
        return repository.getByOwnerAndType(owner, type);
    }
}
