package com.netcracker.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obj_id")
    private int objId;

    private Integer owner;

    private String name;

    private Date date;

    private String type;

    public BasicEntity() {
    }

    public BasicEntity(Integer owner, String name, Date date, String type) {
        this.owner = owner;
        this.name = name;
        this.date = date;
        this.type = type;
    }

    public BasicEntity(Integer objId, Integer owner, String name, Date date, String type) {
        this.owner = owner;
        this.name = name;
        this.date = date;
        this.type = type;
        this.objId = objId;
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ", owner=" + owner +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", type='" + type;
    }
}
