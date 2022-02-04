package com.netcracker.dto;

import lombok.Data;
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
}
