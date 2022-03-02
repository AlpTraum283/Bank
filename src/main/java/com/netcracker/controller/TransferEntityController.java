package com.netcracker.controller;

import com.netcracker.config.JwtProvider;
import com.netcracker.model.dto.database.ObjectDto;
import com.netcracker.model.dto.rest.CreateTransferRequestDto;
import com.netcracker.model.dto.rest.TransferRequestResponseDto;
import com.netcracker.model.entity.AccountEntity;
import com.netcracker.model.entity.TransferRequestEntity;
import com.netcracker.service.EntityProcessorService;
import com.netcracker.service.TransferEntityService;
import com.netcracker.service.TransferRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import static com.netcracker.Constants.*;

@RestController
public class TransferEntityController {

    @Autowired
    TransferEntityService transferEntityService;
    @Autowired
    EntityProcessorService entityProcessorService;
    @Autowired
    TransferRequestService transferRequestService;

    @PostMapping("/transfer")
    public ResponseEntity<TransferRequestResponseDto> createTransferRequest(
            @RequestBody CreateTransferRequestDto createTransferRequestDto, HttpServletRequest request) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Integer userId = Integer.valueOf(new JwtProvider().getLoginFromToken(token));
//        todo: ищем отправителя и получаетеля в базе
        AccountEntity accountEntitySender = entityProcessorService.getEntityByIdAndType(
                AccountEntity.class, createTransferRequestDto.getSender());
        AccountEntity accountEntityRecipient = entityProcessorService.getEntityByIdAndType(
                AccountEntity.class, createTransferRequestDto.getRecipient());
//        todo: проверяем на владение аккаунтом отправителя
        if (accountEntitySender.getOwner() != userId) {
            return ResponseEntity.status(401).body(
                    new TransferRequestResponseDto(
                            0, TRANSACTION_STATUS_ERROR, "No access to account with id " + accountEntitySender.getObjId())

            );
        }

//      todo: создаем транзакцию
        TransferRequestEntity transferRequestEntity = new TransferRequestEntity(
                createTransferRequestDto.getSender(),
                String.format("TRANSFER_FROM_%d_TO_%d", createTransferRequestDto.getSender(), createTransferRequestDto.getRecipient()),
                new Date(),
                OBJECT_TYPE_TRANSFER_REQUEST,
                createTransferRequestDto.getSender(),
                createTransferRequestDto.getRecipient(),
                createTransferRequestDto.getSum()
        );
//        todo: проверяем валидность
        if ((accountEntityRecipient == null) | (accountEntityRecipient == null)) {
            return ResponseEntity.status(400).body(
                    new TransferRequestResponseDto(
                            0, TRANSACTION_STATUS_ERROR, "Incorrect sender and/or recipient")
            );
        }

        if (accountEntitySender.getBalance() < createTransferRequestDto.getSum()) {
            return ResponseEntity.status(400).body(
                    new TransferRequestResponseDto(
                            0, TRANSACTION_STATUS_ERROR, "Not enough money in the balance by account: " + createTransferRequestDto.getSender())
            );
        }
        ObjectDto objectDto = transferEntityService.saveTransferRequest(transferRequestEntity);

        TransferRequestResponseDto response = new TransferRequestResponseDto(
                objectDto.getObjId()
        );
        transferRequestService.doTransferRequest(objectDto.getObjId());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/transfer/{id}")
    public ResponseEntity<TransferRequestResponseDto> getTransferStatusById(@PathVariable("id") Integer id, HttpServletRequest request) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        TransferRequestEntity transferRequestEntity =
                entityProcessorService.getEntityByIdAndType(TransferRequestEntity.class, id);
        if (transferRequestEntity == null) {
            return ResponseEntity.status(400).body(
                    new TransferRequestResponseDto(
                            0, TRANSACTION_STATUS_ERROR, "No such transaction")
            );
        }
        if (TRANSACTION_STATUS_ERROR.equals(transferRequestEntity.getStatus())) {
            return ResponseEntity.status(400).body(
                    new TransferRequestResponseDto(
                            transferRequestEntity.getObjId(), TRANSACTION_STATUS_ERROR, "Transaction failed")
            );
        }
        AccountEntity accountEntity = entityProcessorService.getEntityByIdAndType(AccountEntity.class, transferRequestEntity.getSender());
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Integer userId = Integer.valueOf(new JwtProvider().getLoginFromToken(token));
        if(accountEntity.getOwner() != userId){
            return ResponseEntity.status(401).body(new TransferRequestResponseDto(
                    id, null, "Access denied")
            );
        }
        TransferRequestResponseDto responseDto = new TransferRequestResponseDto(
                transferRequestEntity.getObjId(),
                transferRequestEntity.getStatus()
        );

        return ResponseEntity.ok().body(responseDto);
    }

}
