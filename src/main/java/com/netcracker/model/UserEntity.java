package com.netcracker.model;

import com.netcracker.annotation.Attribute;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class UserEntity extends BasicEntity {

    @Attribute(value = 3)
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                super.toString() +
                ", password='" + password + '\'' +
                '}';
    }

    public UserEntity() {
    }

//    public UserEntity(Integer owner, String name, Date date, String type, String password) {
//        super(owner, name, date, type);
//        this.password = password;
//    }

    public UserEntity(Integer objId, Integer owner, String name, Date date, String type) {
        super(objId, owner, name, date, type);
    }
}
