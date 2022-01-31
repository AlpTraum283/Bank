package com.netcracker;

import com.netcracker.model.User;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringBoot implements CommandLineRunner {

    @Autowired
    UserService userService;
    public static void main(String[] args) {
        SpringApplication.run(SpringBoot.class);


    }

    @Override
    public void run(String... args) {

        List<User> userList= userService.userSelection();
        for (User user: userList) {
            System.out.println(user.toString());
        }
        System.out.println("--------------");

    }
}
