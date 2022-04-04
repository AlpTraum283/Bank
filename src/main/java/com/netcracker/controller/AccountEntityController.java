package com.netcracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.config.JwtProvider;
import com.netcracker.model.dto.rest.AccountEntityResponseDto;
import com.netcracker.model.entity.AccountEntity;
import com.netcracker.model.entity.TransferEntity;
import com.netcracker.service.AccountEntityService;
import com.netcracker.service.EntityProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
public class AccountEntityController {

    @Autowired
    AccountEntityService accountEntityService;
    @Autowired
    EntityProcessorService entityProcessorService;

    @GetMapping("/account/{id}")
    public ResponseEntity getAccountById(@PathVariable("id") Integer id, HttpServletRequest request) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, JsonProcessingException {

//        todo: get userId from token here
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Integer userId = Integer.valueOf(new JwtProvider().getLoginFromToken(token));

        AccountEntity accountEntity = entityProcessorService.getEntityByIdAndType(AccountEntity.class, id);
        if (accountEntity == null)
            return ResponseEntity.status(404).body("No such account with id " + id);
        if (accountEntity.getOwner() != userId) {
            return ResponseEntity.status(401).body("No access to account with id " + id);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObject = mapper.readTree(accountEntity.getInfo());

        return ResponseEntity.ok().body(jsonObject);
    }


    @GetMapping("/account/{id}/operations")
    public AccountEntityResponseDto getAccountOperationsByTimeFrame(
            @PathVariable("id") Integer id,
            @RequestParam(value = "start_date", defaultValue = "20000101") String startDateParam,
            @RequestParam(value = "end_date", defaultValue = "20231212") String endDateParam,
            @RequestParam(value = "page", defaultValue = "1") Integer pageAmount,
            @RequestParam(value = "items", defaultValue = "10") Integer items,
            @RequestHeader("Authorization") String header) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, JsonProcessingException {

        Date startDate = java.sql.Date.valueOf(LocalDate.parse(startDateParam, DateTimeFormatter.BASIC_ISO_DATE));
        Date endDate = java.sql.Date.valueOf(LocalDate.parse(endDateParam, DateTimeFormatter.BASIC_ISO_DATE));

        String token = header.substring(7);
        Integer userId = Integer.valueOf(new JwtProvider().getLoginFromToken(token));

//        todo: получаем список транзакций по id аккаунта
        List<TransferEntity> transferEntityList = accountEntityService.getTransferListByAccountId(id);
        AccountEntity accountEntity = entityProcessorService.getEntityByIdAndType(AccountEntity.class, id);
//        todo: заполняем список операций, подходящих по дате
        AccountEntityResponseDto accountEntityResponseDto = new AccountEntityResponseDto(accountEntity);

        if (accountEntity.getOwner() != userId) {
            return null;
        }

        for (TransferEntity entity : transferEntityList) {
            if (startDate.before(entity.getDate()) && endDate.after(entity.getDate())) {
                accountEntityResponseDto.addOperation(entity);
            }
        }

        return accountEntityResponseDto;


    }
}
