package com.netcracker.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "object")
@Data
public class Object {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer obj_id;

    private Integer parent_id;

    private String name;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    private String type;


    public Integer getObj_id() {
        return obj_id;
    }

    public Integer getParent_id() {
        return parent_id;
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
