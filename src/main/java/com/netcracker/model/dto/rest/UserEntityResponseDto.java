package com.netcracker.model.dto.rest;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UserEntityResponseDto {
    private String name;

    private List<Account> accounts = new ArrayList<>();

    public static class Account {

        private int accountId;

        private long balance;

        public Account(int accountId, long balance) {
            this.accountId = accountId;
            this.balance = balance;
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public long getBalance() {
            return balance;
        }

        public void setBalance(long balance) {
            this.balance = balance;
        }
    }

    public UserEntityResponseDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getOperations() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
