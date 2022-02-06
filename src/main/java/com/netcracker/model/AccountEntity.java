package com.netcracker.model;

import com.netcracker.annotation.Attribute;


import java.util.Date;

public class AccountEntity extends BasicEntity{

    @Attribute(value = 6)
    private String currency;

    @Attribute(value = 1)
    private long balance;

    @Attribute(value = 2)
    private long draft;

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public void setDraft(long draft) {
        this.draft = draft;
    }


    public AccountEntity() {
    }

    public AccountEntity(int objId, int owner, String name, Date date, String type, String currency, long balance, long draft) {
        super(objId, owner, name, date, type);
        this.currency = currency;
        this.balance = balance;
        this.draft = draft;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                super.toString() +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                ", draft=" + draft +
                '}';
    }
}
