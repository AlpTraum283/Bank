package com.netcracker.dto;

import com.netcracker.model.CompositeId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "parameter")
@Data
@IdClass(CompositeId.class)
public class ParameterDto implements Serializable {

    @Id
    @Column(name = "obj_id")
    private Integer objId;

    @Id
    @Column(name = "attr_id")
    private Integer attrId;

    private String value;

    public Integer getObjId() {
        return objId;
    }

    public Integer getAttrId() {
        return attrId;
    }

    public String getValue() {
        return value;
    }
}
