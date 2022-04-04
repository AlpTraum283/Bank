package com.netcracker.model.entity;

import com.netcracker.annotation.Attribute;

import java.util.Date;


public class TransferEntity extends BasicEntity {

    @Attribute(value = 7)
    private int sender;

    @Attribute(value = 8)
    private int recipient;

    @Attribute(value = 4)
    private String operation;

    @Attribute(value = 5)
    private long sum;

    public TransferEntity() {
    }

    public TransferEntity(Integer objId, Integer owner, String name, Date date, String type) {
        super(objId, owner, name, date, type);
    }

    public TransferEntity(Integer owner, int sender, String name, Date date, String type, int recipient, String operation, long sum) {
        super(owner, name, date, type);
        this.sender = sender;
        this.recipient = recipient;
        this.operation = operation;
        this.sum = sum;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getRecipient() {
        return recipient;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }
}
