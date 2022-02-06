package com.netcracker;

import com.netcracker.model.AccountEntity;
import com.netcracker.model.TransferEntity;
import com.netcracker.model.UserEntity;
import com.netcracker.service.EntityProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import static com.netcracker.Constants.*;

@SpringBootApplication
public class SpringBoot implements CommandLineRunner {

    @Autowired
    EntityProcessorService service;

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot.class);


    }

    @Override
    public void run(String... args) {

        try {
            service.getEntityById(UserEntity.class, 1);
            service.getEntityById(AccountEntity.class, 3);
            service.getEntityById(TransferEntity.class, 7);

            service.saveEntity(new UserEntity(11,0, "SavingUserOne", new Date(), OBJECT_TYPE_USER, "savingUserPassword"));
            service.saveEntity(new TransferEntity(11,8, "SavingTransfer", new Date(), OBJECT_TYPE_TRANSFER, 4, "Transfer", 600L));
            service.saveEntity(new AccountEntity(11,8,"savingAccount",new Date(), OBJECT_TYPE_ACCOUNT, "RUB",800L, 0L));
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
