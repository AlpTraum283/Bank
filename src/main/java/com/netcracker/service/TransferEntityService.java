package com.netcracker.service;

import com.netcracker.model.dto.database.ObjectDto;
import com.netcracker.model.entity.TransferRequestEntity;
import com.netcracker.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class TransferEntityService {

    @Autowired
    ObjectRepository repository;
    @Autowired
    EntityProcessorService service;

    public ObjectDto saveTransferRequest(TransferRequestEntity entity) throws IllegalAccessException {
        return service.saveEntity(entity);

    }
}
