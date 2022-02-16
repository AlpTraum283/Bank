package com.netcracker.model.dto.rest;

import com.google.gson.Gson;
import com.netcracker.model.entity.TransferEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountEntityResponseDto {

    private int accountId;

    private List<Operation> operations = new ArrayList<>();

    public static class Operation {

        private String type;

        private long sum;

        private Date date;

        public Operation(String type, long sum, Date date) {
            this.type = type;
            this.sum = sum;
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getSum() {
            return sum;
        }

        public void setSum(long sum) {
            this.sum = sum;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;

    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);

    }

    public AccountEntityResponseDto(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
