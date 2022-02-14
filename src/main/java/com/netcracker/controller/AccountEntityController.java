package com.netcracker.controller;

import com.netcracker.service.AccountEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class AccountEntityController {

    @Autowired
    AccountEntityService service;

    @GetMapping("/account/{id}")
    public ResponseEntity getAccountById(@PathVariable("id") Integer id) {

        Map<String, String> response = service.selectAccountById(id);
        if (response == null)
            return null;
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/account/{id}/operations")
    public ResponseEntity getAccountOperationsByTimeFrame(
            @PathVariable("id") Integer id,
            @RequestParam(value = "start_date", defaultValue = "20000101") String startDateParam,
            @RequestParam(value = "end_date", defaultValue = "20231212") String endDateParam,
            @RequestParam(value = "page", defaultValue = "1") Integer pageAmount,
            @RequestParam(value = "items", defaultValue = "10") Integer items) {

        Date startDate = java.sql.Date.valueOf(LocalDate.parse(startDateParam, DateTimeFormatter.BASIC_ISO_DATE));
        Date endDate = java.sql.Date.valueOf(LocalDate.parse(endDateParam, DateTimeFormatter.BASIC_ISO_DATE));

//        todo: вернуть данные об аккаунте как String
        String account = "account_id: " + service.returnAccountById(id);
//        todo: вернуть данные об операциях, принадлежащих аккаунту как List<Map<String, String>>
        List<Map<String, String>> operations = service.selectOperationsByAccountId(id, startDate, endDate);
//        todo: добавить в будущем обработку параметров page и items, для отображения в браузере
        Map<String, List<Map<String, String>>> response = new HashMap<>();
        response.put(account, operations);


        return ResponseEntity.ok().body(response);
    }
}
