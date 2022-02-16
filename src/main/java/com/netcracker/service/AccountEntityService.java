package com.netcracker.service;

import com.netcracker.model.dto.database.ObjectDto;
import com.netcracker.model.entity.AccountEntity;
import com.netcracker.model.entity.TransferEntity;
import com.netcracker.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static com.netcracker.Constants.OBJECT_TYPE_ACCOUNT;
import static com.netcracker.Constants.OBJECT_TYPE_TRANSFER;

@Service
public class AccountEntityService {

    @Autowired
    ObjectRepository repository;
    @Autowired
    EntityProcessorService service;

    //      Возвращает список операций, проведенных с участием аккаунта
    public List<TransferEntity> getTransferListByAccountId(Integer id) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        List<ObjectDto> objectDtoList = repository.getByOwnerAndType(id, OBJECT_TYPE_TRANSFER);
        List<TransferEntity> transferEntityList = new ArrayList<>();
        for (ObjectDto sample : objectDtoList) {
            TransferEntity transferEntity =
                    (TransferEntity) service.getEntityByIdAndType(TransferEntity.class, sample.getObjId());
            transferEntityList.add(transferEntity);


        }
        return transferEntityList;
    }
}
