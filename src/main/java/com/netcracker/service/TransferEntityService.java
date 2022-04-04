package com.netcracker.service;

import com.netcracker.model.dto.database.ObjectDto;
import com.netcracker.model.entity.TransferRequestEntity;
import com.netcracker.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.netcracker.Constants.OBJECT_TYPE_TRANSFER_REQUEST;

@Service

public class TransferEntityService {

    @Autowired
    ObjectRepository repository;
    @Autowired
    EntityProcessorService service;

    public ObjectDto saveTransferRequest(TransferRequestEntity entity) throws IllegalAccessException {
        return service.saveEntity(entity);

    }

    public List<ObjectDto> getRequests(Integer userId) {
        return repository.getByOwnerAndType(userId, OBJECT_TYPE_TRANSFER_REQUEST);
    }
}
