package com.netcracker.model;

import com.netcracker.annotation.Attribute;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class UserEntity extends BasicEntity {

    private int owner;

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
        return "UserEntity{" +
                "id='" + super.getObjId() + '\'' +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", date=" + date +
                '}';
    }

    public UserEntity(Integer id) {
        super(id);
    }
}
