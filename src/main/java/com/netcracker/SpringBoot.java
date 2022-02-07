package com.netcracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringBoot{

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot.class);
    }

//    @Override
//    public void run(String... args) {
//
//        try {
////            service.getEntityById(UserEntity.class, 1);
////            service.getEntityById(AccountEntity.class, 3);
////            service.getEntityById(TransferEntity.class, 7);
//
//            service.saveEntity(new UserEntity(0, "SavingUser88", new Date(), OBJECT_TYPE_USER, "savingUserPassword"));
//            service.saveEntity(new TransferEntity(8, "SavingTransfer88", new Date(), OBJECT_TYPE_TRANSFER, 4, "Transfer", 600L));
//            service.saveEntity(new AccountEntity(8,"savingAccount88",new Date(), OBJECT_TYPE_ACCOUNT, "RUB",800L, 0L));
//        } catch (IllegalAccessException /*| InstantiationException | NoSuchMethodException | InvocationTargetException | NoSuchFieldException*/ e) {
//            e.printStackTrace();
//        }
//
//    }
}
