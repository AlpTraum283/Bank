package com.netcracker.controller;

import com.netcracker.service.AccountEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
}
