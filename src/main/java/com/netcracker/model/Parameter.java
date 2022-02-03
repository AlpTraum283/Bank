package com.netcracker.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "parameter")
@Data
@IdClass(CompositeId.class)
public class Parameter implements Serializable {

    @Id
    private Integer obj_id;

    @Id
    private Integer attr_id;

    private String value;

    public int getObj_id() {
        return obj_id;
    }

    public int getAttr_id() {
        return attr_id;
    }

    public String getValue() {
        return value;
    }
}
