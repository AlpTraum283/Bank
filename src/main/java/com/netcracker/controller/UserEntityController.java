package com.netcracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.model.dto.database.ObjectDto;
import com.netcracker.model.dto.rest.LoginPostDto;
import com.netcracker.model.dto.rest.UserEntityResponseDto;
import com.netcracker.model.entity.AccountEntity;
import com.netcracker.model.entity.UserEntity;
import com.netcracker.service.EntityProcessorService;
import com.netcracker.service.UserEntityService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    public static Integer authorizedUserId;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserEntityResponseDto> getUserAndAccountsById(@PathVariable(value = "id") Integer id) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, JsonProcessingException {

        UserEntity userEntity = entityProcessorService.getEntityByIdAndType(UserEntity.class, id);
        List<AccountEntity> accountEntityList = userEntityService.getAccountListByOwner(userEntity.getObjId());
        if (!UserEntityController.authorizedUserId.equals(id)) {
            return null;
        }
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

    @PostMapping("/login")
    public ResponseEntity logIn(@RequestBody LoginPostDto login) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        UserEntity userEntity = entityProcessorService.getEntityByNameAndType(UserEntity.class, login.getUserName());
        if (userEntity.getPassword().equals(login.getPassword())) {
            authorizedUserId = userEntity.getObjId();
            return ResponseEntity.ok().body("Login successful");
        }
        return ResponseEntity.status(400).body("Incorrect login data");
    }
}
