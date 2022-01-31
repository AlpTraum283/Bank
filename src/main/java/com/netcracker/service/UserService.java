package com.netcracker.service;

import com.netcracker.model.User;
import com.netcracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public List<User> userSelection() {
        return repository.selectUser();
    }

}
