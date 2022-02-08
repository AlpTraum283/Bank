package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.UserEntity;
import com.netcracker.service.EntityProcessorService;
import com.netcracker.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserEntityController {

    @Autowired
    UserEntityService service;

    @GetMapping("/user/{id}")
    public ResponseEntity getUserAndAccountsById(@PathVariable(value = "id") Integer id){
        String user = service.selectUserById(id);
        List<String> accounts = service.selectAccountsByUserId(id);
        Map<String,List<String>> response = new HashMap<>();
        response.put(user,accounts);

        if (user == null | accounts == null)
            return null;
        return ResponseEntity.ok().body(response);

    }
}
