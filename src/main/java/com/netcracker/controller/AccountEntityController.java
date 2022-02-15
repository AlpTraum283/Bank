package com.netcracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.model.dto.database.ObjectDto;
import com.netcracker.model.dto.rest.AccountGetMappingDto;
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

import static com.netcracker.Constants.OBJECT_TYPE_TRANSFER;

@RestController
public class AccountEntityController {

    @Autowired
    AccountEntityService accountEntityService;
    @Autowired
    EntityProcessorService entityProcessorService;

    @GetMapping("/account/{id}")
    public ResponseEntity getAccountById(@PathVariable("id") Integer id) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, JsonProcessingException {


        AccountEntity accountEntity =
                (AccountEntity) entityProcessorService.getEntityByIdAndType(AccountEntity.class, id);
        if (accountEntity == null)
            return null;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObject = mapper.readTree(accountEntity.getInfo());

        return ResponseEntity.ok().body(jsonObject);
    }


    @GetMapping("/account/{id}/operations")
    public ResponseEntity getAccountOperationsByTimeFrame(
            @PathVariable("id") Integer id,
            @RequestParam(value = "start_date", defaultValue = "20000101") String startDateParam,
            @RequestParam(value = "end_date", defaultValue = "20231212") String endDateParam,
            @RequestParam(value = "page", defaultValue = "1") Integer pageAmount,
            @RequestParam(value = "items", defaultValue = "10") Integer items) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, JsonProcessingException {

        Date startDate = java.sql.Date.valueOf(LocalDate.parse(startDateParam, DateTimeFormatter.BASIC_ISO_DATE));
        Date endDate = java.sql.Date.valueOf(LocalDate.parse(endDateParam, DateTimeFormatter.BASIC_ISO_DATE));

//        todo: получаем список транзакций по id аккаунта
        List<TransferEntity> transferEntityList = accountEntityService.getTransferListByAccountId(id);

        AccountGetMappingDto accountGetMappingDto = new AccountGetMappingDto(id);
        for (TransferEntity entity : transferEntityList) {
            if (startDate.before(entity.getDate()) && endDate.after(entity.getDate())) {
                accountGetMappingDto.addOperation(new AccountGetMappingDto.Operation(
                        entity.getOperation(),
                        entity.getSum(),
                        entity.getDate()
                ));
            }
        }
//        todo: заменить нижестоящий код restDto моделью, и сделать это там
//        StringBuilder transferFormattedList = new StringBuilder("");
//        int counter = 1;
//        for (TransferEntity entity : transferEntityList) {
//
//            transferFormattedList.append("{");
//            transferFormattedList.append("\"type\":\"").append(entity.getOperation()).append("\"");
//            transferFormattedList.append(",\"sum\": \"").append(entity.getSum()).append("\"");
//            if (counter < transferEntityList.size()) {
//                transferFormattedList.append(",\"date\": \"").append(entity.getDate()).append("\"},");
//                counter++;
//            } else {
//                transferFormattedList.append(",\"date\": \"").append(entity.getDate()).append("\"}");
//            }
//
//        }
        String response = "{" +
                "\"account_id\": \"" + id +
                "\", \"operations\": [" + "transferFormattedList" +
                "]" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObject = mapper.readTree(accountGetMappingDto.toString());
        return ResponseEntity.ok().body(jsonObject);
    }
}
