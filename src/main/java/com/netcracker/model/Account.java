package com.netcracker.model;

import com.netcracker.annotation.Attribute;


import java.util.Date;

public class Account extends BasicEntity{

    private int owner;

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
                "id=" + super.getId() +
                ", owner=" + owner +
                ", date=" + date +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                ", draft=" + draft +
                '}';
    }

    public Account(Integer id) {
        super(id);
    }
}
