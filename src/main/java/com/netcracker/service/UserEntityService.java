package com.netcracker.service;

import com.netcracker.model.dto.database.ObjectDto;
import com.netcracker.model.entity.AccountEntity;
import com.netcracker.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static com.netcracker.Constants.OBJECT_TYPE_ACCOUNT;


@Service
public class UserEntityService {

    @Autowired
    ObjectRepository repository;
    @Autowired
    EntityProcessorService service;

    public List<AccountEntity> getAccountListByOwner(Integer owner) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        List<ObjectDto> objectDtoList = repository.getByOwnerAndType(owner,OBJECT_TYPE_ACCOUNT);
        List<AccountEntity> accountEntityList = new ArrayList<>();
        for (ObjectDto sample : objectDtoList) {
            AccountEntity accountEntity =
                    (AccountEntity) service.getEntityByIdAndType(AccountEntity.class, sample.getObjId());
            accountEntityList.add(accountEntity);
        }
        return accountEntityList;
    }
}
