package com.netcracker.model;

import com.netcracker.annotation.Attribute;

import java.util.Date;


public class TransferEntity extends BasicEntity {

    private int owner;

    private String name;

    @Attribute(value = 8)
    private int recipient;

    @Attribute(value = 4)
    private String operation;

    private Date date;

    @Attribute(value = 5)
    private long sum;

    public void setSender(int owner) {
        this.owner = owner;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + super.getObjId() +
                ", owner=" + owner +
                ", recipient=" + recipient +
                ", operation='" + operation + '\'' +
                ", date=" + date +
                ", sum=" + sum +
                '}';
    }

    public TransferEntity(Integer id) {
        super(id);
    }
}
