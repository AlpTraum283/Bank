package com.netcracker.service;

import com.netcracker.model.entity.AccountEntity;
import com.netcracker.model.entity.TransferEntity;
import com.netcracker.model.entity.TransferRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.concurrent.Future;

import static com.netcracker.Constants.*;

@EnableAsync
@Service
public class TransferRequestService {
    @Autowired
    EntityProcessorService entityProcessorService;
    @Autowired
    AccountEntityService accountEntityService;


    @Transactional
    @Async
    public Future<Integer> doTransferRequest(Integer id) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        TransferRequestEntity transferRequestEntity = entityProcessorService.getEntityByIdAndType(TransferRequestEntity.class, id);
        transferRequestEntity.setStatus(TRANSACTION_STATUS_IN_PROGRESS);
        entityProcessorService.saveEntity(transferRequestEntity);

//        todo: взять 2 акканунта из базы - получатель и отправитель
        AccountEntity senderAccount = entityProcessorService.getEntityByIdAndType(AccountEntity.class, transferRequestEntity.getSender());
        AccountEntity recipientAccount = entityProcessorService.getEntityByIdAndType(AccountEntity.class, transferRequestEntity.getRecipient());

//        todo: провести операции перевода - на draft,
        senderAccount.setBalance(senderAccount.getBalance() - transferRequestEntity.getSum());
        senderAccount.setDraft(senderAccount.getDraft() + transferRequestEntity.getSum());

        recipientAccount.setBalance(recipientAccount.getBalance() + transferRequestEntity.getSum());
        senderAccount.setDraft(senderAccount.getDraft() - transferRequestEntity.getSum());

//        todo: создать 2 Transfer, по 1 для каждого из участников
        TransferEntity transferEntitySender = new TransferEntity(
                transferRequestEntity.getOwner(),
                String.format("TRANSFER_FROM_%d_TO_%d", transferRequestEntity.getSender(), transferRequestEntity.getRecipient()),
                new Date(),
                OBJECT_TYPE_TRANSFER,
                transferRequestEntity.getRecipient(),
                OPERATION_TYPE_OUT,
                transferRequestEntity.getSum()
        );
        TransferEntity transferEntityRecipient = new TransferEntity(
                transferRequestEntity.getRecipient(),
                String.format("TRANSFER_FROM_%d_TO_%d", transferRequestEntity.getSender(), transferRequestEntity.getRecipient()),
                new Date(),
                OBJECT_TYPE_TRANSFER,
                transferRequestEntity.getRecipient(),
                OPERATION_TYPE_IN,
                transferRequestEntity.getSum()
        );

//        todo: сохраняем новые и обновляем старые сущности
        entityProcessorService.saveEntity(transferEntitySender);
        entityProcessorService.saveEntity(transferEntityRecipient);
        entityProcessorService.saveEntity(senderAccount);
        entityProcessorService.saveEntity(recipientAccount);

//        todo: обновляем статус перевода
        transferRequestEntity.setStatus(TRANSACTION_STATUS_SUCCESS);
        entityProcessorService.saveEntity(transferRequestEntity);

        return new AsyncResult<>(transferRequestEntity.getObjId());
    }
}
