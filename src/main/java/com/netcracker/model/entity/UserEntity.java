package com.netcracker.model.entity;

import com.netcracker.annotation.Attribute;

import java.util.Date;


public class UserEntity extends BasicEntity {

    @Attribute(value = 3)
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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


    public UserEntity(Integer objId, Integer owner, String name, Date date, String type) {
        super(objId, owner, name, date, type);
    }
}
