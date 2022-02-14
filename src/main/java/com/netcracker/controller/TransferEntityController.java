package com.netcracker.controller;

import com.netcracker.model.TransferEntity;
import com.netcracker.model.TransferRequest;
import com.netcracker.service.TransferEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.netcracker.Constants.*;

@RestController
public class TransferEntityController {

    @Autowired
    TransferEntityService service;

    @PostMapping("/transfer")
    public ResponseEntity createTransferRequest(@RequestBody TransferRequest transferRequest) {

        if (transferRequest == null)
            return null;

//        todo: проверить наличие средств у sender`а,
        Integer accountBalance = service.selectBalanceByAccountId(transferRequest.getSender());
//        todo: если все в порядке - создать сущность Transfer, заполнить соответствующие поля(Date, sender, recipient, sum)
        if (accountBalance < transferRequest.getSum())
            return ResponseEntity.badRequest().body("Недостаточно средств на аккаунте с id=" + transferRequest.getSender());

        TransferEntity transferEntity = new TransferEntity(
                transferRequest.getSender(),
                "Transfer " + transferRequest.getId(),
                transferRequest.getDate(),
                OBJECT_TYPE_TRANSFER,
                transferRequest.getRecipient(),
                OPERATION_TYPE_OUT,
                transferRequest.getSum()
        );


        return ResponseEntity.ok().body(transferEntity);
    }


}
