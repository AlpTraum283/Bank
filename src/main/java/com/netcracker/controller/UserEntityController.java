package com.netcracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.config.JwtProvider;
import com.netcracker.model.dto.database.ObjectDto;
import com.netcracker.model.dto.rest.LoginPostDto;
import com.netcracker.model.dto.rest.UserEntityResponseDto;
import com.netcracker.model.entity.AccountEntity;
import com.netcracker.model.entity.UserEntity;
import com.netcracker.service.EntityProcessorService;
import com.netcracker.service.UserEntityService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;

import static com.netcracker.Constants.OBJECT_TYPE_ACCOUNT;

@RestController
public class UserEntityController {

    @Autowired
    UserEntityService userEntityService;
    @Autowired
    EntityProcessorService entityProcessorService;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserEntityResponseDto> getUserAndAccountsById(@PathVariable(value = "id") Integer id, HttpServletRequest request) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, JsonProcessingException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Integer userId = Integer.valueOf(new JwtProvider().getLoginFromToken(token));
        if (!userId.equals(id)) {
            return ResponseEntity.status(401).body(null);
        }
        UserEntity userEntity = entityProcessorService.getEntityByIdAndType(UserEntity.class, id);
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

    @GetMapping("/login")
    public ResponseEntity logIn(@RequestHeader("Authorization") String loginPasswordBase64) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tmp = loginPasswordBase64.replace("Basic ", "");
        String[] credentials = new String(Base64.getDecoder().decode(tmp.getBytes()), StandardCharsets.UTF_8).split(":");
        UserEntity userEntity = entityProcessorService.getEntityByNameAndType(UserEntity.class, credentials[0]);
        if (userEntity.getPassword().equals(credentials[1])) {
            String jwtToken = new JwtProvider().generateToken(userEntity.getObjId());
            return ResponseEntity.ok().body("Login successful. Here is your token: " + jwtToken);
        }
        return ResponseEntity.status(400).body("Incorrect login data");
    }
}
