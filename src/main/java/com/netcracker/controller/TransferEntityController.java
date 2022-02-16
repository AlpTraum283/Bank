package com.netcracker.controller;

import com.netcracker.model.dto.database.ObjectDto;
import com.netcracker.model.dto.rest.CreateTransferRequestDto;
import com.netcracker.model.dto.rest.TransferRequestResponseDto;
import com.netcracker.model.entity.AccountEntity;
import com.netcracker.model.entity.TransferRequestEntity;
import com.netcracker.service.EntityProcessorService;
import com.netcracker.service.TransferEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import static com.netcracker.Constants.*;

@RestController
public class TransferEntityController {

    @Autowired
    TransferEntityService transferEntityService;
    @Autowired
    EntityProcessorService entityProcessorService;

    @PostMapping("/transfer")
    public ResponseEntity<TransferRequestResponseDto> createTransferRequest(
            @RequestBody CreateTransferRequestDto createTransferRequestDto) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
//      todo: создаем транзакцию
        TransferRequestEntity transferRequestEntity = new TransferRequestEntity(
                createTransferRequestDto.getSender(),
                String.format("TRANSFER_OUT_FROM_%d_TO_%d", createTransferRequestDto.getSender(), createTransferRequestDto.getRecipient()),
                new Date(),
                OBJECT_TYPE_TRANSFER_REQUEST,
                createTransferRequestDto.getSender(),
                createTransferRequestDto.getRecipient(),
                createTransferRequestDto.getSum()
        );

//        todo: ищем отправителя и получаетеля в базе
        AccountEntity accountEntitySender = (AccountEntity) entityProcessorService.getEntityByIdAndType(
                AccountEntity.class, createTransferRequestDto.getSender());
        AccountEntity accountEntityRecipient = (AccountEntity) entityProcessorService.getEntityByIdAndType(
                AccountEntity.class, createTransferRequestDto.getRecipient());

//        todo: проверяем валидность
        if ((accountEntityRecipient == null) | (accountEntityRecipient == null)) {
            return ResponseEntity.ok().body(
                    new TransferRequestResponseDto(
                            0, TRANSACTION_STATUS_ERROR, "Incorrect sender and/or recipient")
            );
        }

        if (accountEntitySender.getBalance() < createTransferRequestDto.getSum()) {
            return ResponseEntity.ok().body(
                    new TransferRequestResponseDto(
                            0, TRANSACTION_STATUS_ERROR, "Not enough money in the balance by account: " + createTransferRequestDto.getSender())
            );
        }
        ObjectDto objectDto = transferEntityService.saveTransferRequest(transferRequestEntity);

        TransferRequestResponseDto response = new TransferRequestResponseDto(
                objectDto.getObjId()
        );

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/transfer/{id}")
    public ResponseEntity<TransferRequestResponseDto> getTransferStatusById(@PathVariable("id") Integer id) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        TransferRequestEntity transferRequestEntity =
                (TransferRequestEntity) entityProcessorService.getEntityByIdAndType(TransferRequestEntity.class, id);
        if (transferRequestEntity == null) {
            return ResponseEntity.ok().body(
                    new TransferRequestResponseDto(
                            0, TRANSACTION_STATUS_ERROR, "No such transaction")
            );
        }
        if(TRANSACTION_STATUS_ERROR.equals(transferRequestEntity.getStatus())){
            return ResponseEntity.ok().body(
                    new TransferRequestResponseDto(
                            transferRequestEntity.getObjId(), TRANSACTION_STATUS_ERROR, "Something went wrong")
            );
        }
        TransferRequestResponseDto responseDto = new TransferRequestResponseDto(
                transferRequestEntity.getObjId(),
                transferRequestEntity.getStatus()
        );

        return ResponseEntity.ok().body(responseDto);
    }

}
