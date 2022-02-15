package com.netcracker.model.dto.rest;

import com.netcracker.model.entity.TransferEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountGetMappingDto {

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

        @Override
        public String toString() {
            return "{" +
                    "\"type\": \"" + type +
                    "\",\"sum\": \"" + sum +
                    "\",\"date\": \"" + date +
                    "\"}";
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

    public AccountGetMappingDto(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        String operationsList = "";
        int counter = 1;
        for (Operation operation : this.operations) {
            if (counter < operations.size()) {
                operationsList += operation.toString() + ",";
                counter++;
            } else {
                operationsList += operation.toString();
            }
        }

        return "{" +
                "\"account_id\": \"" + this.accountId +
                "\",\"operations\": [" + operationsList +
                "]" +
                "}";
    }
}
