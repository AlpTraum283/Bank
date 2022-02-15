package com.netcracker.controller;

import com.netcracker.dto.ObjectDto;
import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.AccountEntity;
import com.netcracker.model.BasicEntity;
import com.netcracker.model.UserEntity;
import com.netcracker.service.AccountEntityService;
import com.netcracker.service.EntityProcessorService;
import com.netcracker.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.netcracker.Constants.OBJECT_TYPE_ACCOUNT;

@RestController
public class UserEntityController {

    @Autowired
    UserEntityService userEntityService;
    @Autowired
    EntityProcessorService entityProcessorService;
    @Autowired
    AccountEntityService accountEntityService;

    @GetMapping("/user/{id}")
    public ResponseEntity getUserAndAccountsById(@PathVariable(value = "id") Integer id) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        UserEntity userEntity = (UserEntity) entityProcessorService.getEntityByIdAndType(UserEntity.class, id);
        List<AccountEntity> accountEntityList = new ArrayList<>();
        List<ObjectDto> objectDtoList = accountEntityService.getByOwnerAndType(userEntity.getObjId(), OBJECT_TYPE_ACCOUNT);


//        if (userEntity == null || objectDtoList == null)
//            return null;
//
//        for (ObjectDto sample : objectDtoList) {
//            AccountEntity accountEntity =
//                    (AccountEntity) entityProcessorService.getEntityByIdAndType(AccountEntity.class, sample.getObjId());
//            accountEntityList.add(accountEntity);
//        }
//
//        StringBuilder
//        return ResponseEntity.ok().body(response);
        return null;
    }
}
