package com.netcracker.model.dto.database;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "object")
@Data
public class ObjectDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obj_id")
    private Integer objId;

    private Integer owner;

    private String name;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    private String type;

    public Integer getObjId() {
        return objId;
    }

    public Integer getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ObjectDto(Integer owner, String name, Date date, String type) {
        this.owner = owner;
        this.name = name;
        this.date = date;
        this.type = type;
    }

    public ObjectDto() {
    }


}
