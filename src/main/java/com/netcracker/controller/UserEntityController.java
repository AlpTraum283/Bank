package com.netcracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netcracker.config.JwtProvider;
import com.netcracker.config.UserDetailsConfig;
import com.netcracker.model.dto.rest.UserEntityResponseDto;
import com.netcracker.model.entity.AccountEntity;
import com.netcracker.model.entity.UserEntity;
import com.netcracker.service.EntityProcessorService;
import com.netcracker.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RestController
public class UserEntityController {

    @Autowired
    UserEntityService userEntityService;
    @Autowired
    EntityProcessorService entityProcessorService;

    @GetMapping("/user")
    public UserEntityResponseDto getUserAndAccountsById(@RequestHeader("Authorization") String header) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, JsonProcessingException {
        Integer userId = Integer.parseInt(
                ((UserDetailsConfig) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()
                ).getUserId());

        UserEntity userEntity = entityProcessorService.getEntityByIdAndType(UserEntity.class, userId);
        List<AccountEntity> accountEntityList = userEntityService.getAccountListByOwner(userEntity.getObjId());

        UserEntityResponseDto userEntityResponseDto = new UserEntityResponseDto(userEntity, accountEntityList);

        return userEntityResponseDto;
    }

    @GetMapping("/login")
    public String logIn(@RequestHeader("Authorization") String loginPasswordBase64) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tmp = loginPasswordBase64.replace("Basic ", "");
        String[] credentials = new String(Base64.getDecoder().decode(tmp.getBytes()), StandardCharsets.UTF_8).split(":");
        UserEntity userEntity = entityProcessorService.getEntityByNameAndType(UserEntity.class, credentials[0]);
        if (userEntity != null && userEntity.getPassword().equals(credentials[1])) {
            String jwtToken = new JwtProvider().generateToken(userEntity.getObjId());
            return jwtToken;
        }
        return null;
    }
}
