package com.netcracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.model.dto.database.ObjectDto;
import com.netcracker.model.dto.rest.UserEntityResponseDto;
import com.netcracker.model.entity.AccountEntity;
import com.netcracker.model.entity.UserEntity;
import com.netcracker.service.EntityProcessorService;
import com.netcracker.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static com.netcracker.Constants.OBJECT_TYPE_ACCOUNT;

@RestController
public class UserEntityController {

    @Autowired
    UserEntityService userEntityService;
    @Autowired
    EntityProcessorService entityProcessorService;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserEntityResponseDto> getUserAndAccountsById(@PathVariable(value = "id") Integer id) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, JsonProcessingException {

        UserEntity userEntity = (UserEntity) entityProcessorService.getEntityByIdAndType(UserEntity.class, id);
        List<AccountEntity> accountEntityList = userEntityService.getAccountListByOwner(userEntity.getObjId());

        UserEntityResponseDto userEntityResponseDto = new UserEntityResponseDto(userEntity.getName());
        for (AccountEntity entity : accountEntityList) {
            userEntityResponseDto.addAccount(
                    new UserEntityResponseDto.Account(
                            entity.getObjId(),
                            entity.getBalance()
                    ));
        }


        return ResponseEntity.ok().body(userEntityResponseDto);
    }
}
