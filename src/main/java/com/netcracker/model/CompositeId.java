package com.netcracker.model;

import javax.persistence.IdClass;
import java.io.Serializable;


public class CompositeId implements Serializable {
    private int obj_id;
    private int attr_id;
}
