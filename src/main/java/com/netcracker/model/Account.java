package com.netcracker.model;

import com.netcracker.annotation.Attribute;


import java.util.Date;

public class Account extends BasicEntity{

    private int owner;

    private Date date_of_open;

    @Attribute(value = 6)
    private String currency;

    @Attribute(value = 1)
    private long balance;

    @Attribute(value = 2)
    private long draft;

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void setDate_of_open(Date date_of_open) {
        this.date_of_open = date_of_open;
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
                ", date_of_open=" + date_of_open +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                ", draft=" + draft +
                '}';
    }
}
