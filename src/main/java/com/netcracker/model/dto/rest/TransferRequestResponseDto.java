package com.netcracker.model.dto.rest;

import com.google.gson.Gson;

import static com.netcracker.Constants.TRANSACTION_STATUS_NEW;

public class TransferRequestResponseDto {
    private int transactionId;

    private String status = TRANSACTION_STATUS_NEW;

    private String message;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TransferRequestResponseDto() {
    }

    public TransferRequestResponseDto(int transactionId) {
        this.transactionId = transactionId;
    }

    public TransferRequestResponseDto(int transactionId, String status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public TransferRequestResponseDto(int transactionId, String status, String message) {
        this.transactionId = transactionId;
        this.status = status;
        this.message = message;
    }
//    todo: вот тут Null поле сериализуется
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
