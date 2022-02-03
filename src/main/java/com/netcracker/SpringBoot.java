package com.netcracker;

import com.netcracker.model.Account;
import com.netcracker.model.Transfer;
import com.netcracker.model.User;
import com.netcracker.service.EntityProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.InvocationTargetException;

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
            service.getEntityById(User.class, 1);
            service.getEntityById(User.class, 2);
            service.getEntityById(Account.class, 3);
            service.getEntityById(Account.class, 80);
            service.getEntityById(Transfer.class, 7);
            service.getEntityById(Transfer.class, 5);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
