package com.netcracker.controller;

import com.netcracker.model.entity.TransferRequest;
import com.netcracker.service.TransferEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.netcracker.Constants.TRANSACTION_STATUS_ERROR;

@RestController
public class TransferEntityController {

    @Autowired
    TransferEntityService service;

    @PostMapping("/transfer")
    public ResponseEntity createTransferRequest(@RequestBody TransferRequest transferRequest) {

        if (transferRequest == null)
            return null;

        String response = "{" +
                "\"sender\": \"" + transferRequest.getSender() +
                "\",\"status\": \"" + transferRequest.getStatus();
        if (TRANSACTION_STATUS_ERROR.equals(transferRequest.getStatus())) {
            response += "\",\"error_message\": \"" + transferRequest.getMessage() +
                    "\"}";
        } else {
            response += "\"}";
        }

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/transfer/{id}")
    public ResponseEntity getTransferStatusById(@PathVariable("id") Integer id) {


        return null;
    }

}
