package com.netcracker.model;

import com.netcracker.annotation.Attribute;


import java.util.Date;

public class AccountEntity extends BasicEntity{

    private int owner;

    private String name;

    private Date date;

    @Attribute(value = 6)
    private String currency;

    @Attribute(value = 1)
    private long balance;

    @Attribute(value = 2)
    private long draft;

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void setDate_of_open(Date date) {
        this.date = date;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public void setDraft(long draft) {
        this.draft = draft;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + super.getObjId() +
                ", owner=" + owner +
                ", date=" + date +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                ", draft=" + draft +
                '}';
    }

    public AccountEntity(Integer id) {
        super(id);
    }
}
