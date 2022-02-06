package com.netcracker.model;

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

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "TransferEntity{" +
                super.toString() +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", operation='" + operation + '\'' +
                ", sum=" + sum +
                '}';
    }

    public TransferEntity() {
    }

    public TransferEntity(int owner, String name, Date date, String type, int recipient, String operation, long sum) {
        super(owner, name, date, type);
        this.sender = owner;
        this.recipient = recipient;
        this.operation = operation;
        this.sum = sum;
    }
}
