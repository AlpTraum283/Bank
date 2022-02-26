package com.netcracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.model.dto.rest.AccountEntityResponseDto;
import com.netcracker.model.entity.AccountEntity;
import com.netcracker.model.entity.TransferEntity;
import com.netcracker.service.AccountEntityService;
import com.netcracker.service.EntityProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class AccountEntityController {

    @Autowired
    AccountEntityService accountEntityService;
    @Autowired
    EntityProcessorService entityProcessorService;

    @GetMapping("/account/{id}")
    public ResponseEntity getAccountById(@PathVariable("id") Integer id) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, JsonProcessingException {

        AccountEntity accountEntity = entityProcessorService.getEntityByIdAndType(AccountEntity.class, id);
        if (accountEntity == null)
            return null;
        if (UserEntityController.authorizedUserId != accountEntity.getOwner()) {
            return ResponseEntity.status(400).body("You are not the owner of this account.");
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObject = mapper.readTree(accountEntity.getInfo());

        return ResponseEntity.ok().body(jsonObject);
    }


    @GetMapping("/account/{id}/operations")
    public ResponseEntity<AccountEntityResponseDto> getAccountOperationsByTimeFrame(
            @PathVariable("id") Integer id,
            @RequestParam(value = "start_date", defaultValue = "20000101") String startDateParam,
            @RequestParam(value = "end_date", defaultValue = "20231212") String endDateParam,
            @RequestParam(value = "page", defaultValue = "1") Integer pageAmount,
            @RequestParam(value = "items", defaultValue = "10") Integer items) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, JsonProcessingException {

        Date startDate = java.sql.Date.valueOf(LocalDate.parse(startDateParam, DateTimeFormatter.BASIC_ISO_DATE));
        Date endDate = java.sql.Date.valueOf(LocalDate.parse(endDateParam, DateTimeFormatter.BASIC_ISO_DATE));

        AccountEntity accountEntity = entityProcessorService.getEntityByIdAndType(AccountEntity.class, id);
        if (UserEntityController.authorizedUserId != accountEntity.getOwner()) {
            return null;
        }

//        todo: получаем список транзакций по id аккаунта
        List<TransferEntity> transferEntityList = accountEntityService.getTransferListByAccountId(id);
//        todo: заполняем список операций, подходящих по дате
        AccountEntityResponseDto accountEntityResponseDto = new AccountEntityResponseDto(id);
        for (TransferEntity entity : transferEntityList) {
            if (startDate.before(entity.getDate()) && endDate.after(entity.getDate())) {
                accountEntityResponseDto.addOperation(new AccountEntityResponseDto.Operation(
                        entity.getOperation(),
                        entity.getSum(),
                        entity.getDate()
                ));
            }
        }

        return ResponseEntity.ok().body(accountEntityResponseDto);


    }
}
