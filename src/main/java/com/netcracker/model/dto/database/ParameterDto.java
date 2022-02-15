package com.netcracker.model.dto.database;

import com.netcracker.model.entity.CompositeId;
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

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ParameterDto(Integer objId, Integer attrId, String value) {
        this.objId = objId;
        this.attrId = attrId;
        this.value = value;
    }

    public ParameterDto() {
    }
}
