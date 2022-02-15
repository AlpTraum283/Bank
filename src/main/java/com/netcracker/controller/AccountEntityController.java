package com.netcracker.controller;

import com.netcracker.dto.ObjectDto;
import com.netcracker.model.AccountEntity;
import com.netcracker.model.TransferEntity;
import com.netcracker.service.AccountEntityService;
import com.netcracker.service.EntityProcessorService;
import io.swagger.v3.core.util.Json;
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

import static com.netcracker.Constants.OBJECT_TYPE_ACCOUNT;
import static com.netcracker.Constants.OBJECT_TYPE_TRANSFER;

@RestController
public class AccountEntityController {

    @Autowired
    AccountEntityService accountEntityService;
    @Autowired
    EntityProcessorService entityProcessorService;

    @GetMapping("/account/{id}")
    public ResponseEntity getAccountById(@PathVariable("id") Integer id) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {

        ObjectDto objectDto = accountEntityService.getByObjIdAndType(id, OBJECT_TYPE_ACCOUNT);
        if (objectDto == null)
            return null;
        AccountEntity accountEntity =
                (AccountEntity) entityProcessorService.getEntityByIdAndType(AccountEntity.class, id);
        StringBuilder response = new StringBuilder("{");
        response.append("\n\t\"id\": \"").append(accountEntity.getObjId()).append("\"");
        response.append(",\n\t\"balance\": ").append(accountEntity.getBalance());
        response.append(",\n\t\"currency\": \"").append(accountEntity.getCurrency()).append("\"");
        response.append(",\n\t\"date\": ").append(accountEntity.getDate());
        response.append("\n}");
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/account/{id}/operations")
    public ResponseEntity getAccountOperationsByTimeFrame(
            @PathVariable("id") Integer id,
            @RequestParam(value = "start_date", defaultValue = "20000101") String startDateParam,
            @RequestParam(value = "end_date", defaultValue = "20231212") String endDateParam,
            @RequestParam(value = "page", defaultValue = "1") Integer pageAmount,
            @RequestParam(value = "items", defaultValue = "10") Integer items) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {

        Date startDate = java.sql.Date.valueOf(LocalDate.parse(startDateParam, DateTimeFormatter.BASIC_ISO_DATE));
        Date endDate = java.sql.Date.valueOf(LocalDate.parse(endDateParam, DateTimeFormatter.BASIC_ISO_DATE));

//        todo: Вернуть обьекты transfer на основе id, после чего засунуть их в список
        List<ObjectDto> transferObjects = accountEntityService.getByOwnerAndType(id, OBJECT_TYPE_TRANSFER);
        List<TransferEntity> transferEntityList = new ArrayList<>();
//        todo: Парсим обьекты dto на обьекты Transfer, заполняя ими список
        for (ObjectDto sample : transferObjects) {
            TransferEntity transferEntity =
                    (TransferEntity) entityProcessorService.getEntityByIdAndType(TransferEntity.class, sample.getObjId());
            if (startDate.before(transferEntity.getDate()) && endDate.after(transferEntity.getDate())) {
                transferEntityList.add(transferEntity);
            }

        }
//        todo: формируем подстроку в виде выпадающего списка для каждой транзакции
        StringBuilder transferFormattedList = new StringBuilder("[");
        for (TransferEntity entity : transferEntityList) {

            transferFormattedList.append("\n\t{");
            transferFormattedList.append("\n\t  \"type\": \"").append(entity.getOperation()).append("\"");
            transferFormattedList.append(",\n\t  \"sum\": \"").append(entity.getSum()).append("\"");
            transferFormattedList.append(",\n\t  \"date\": \"").append(entity.getDate()).append("\"\n\t}");
        }
        String response = "{ \n" +
                "\t\"account_id\": \"" + accountEntityService.getByObjIdAndType(id, OBJECT_TYPE_ACCOUNT).getObjId() + "\"," +
                "\n\t\"operations\": " + transferFormattedList + "\n\t]" +
                "\n}";


        return ResponseEntity.ok().body(response);
    }
}
