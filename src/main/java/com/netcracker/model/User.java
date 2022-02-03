package com.netcracker.model;

import com.netcracker.annotation.Attribute;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class User extends BasicEntity {

    private String name;

    @Attribute(value = 3)
    private String password;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + super.getId() + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", date=" + date +
                '}';
    }

    public User(Integer id) {
        super(id);
    }
}
