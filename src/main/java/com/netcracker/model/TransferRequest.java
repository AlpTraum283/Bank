package com.netcracker.model;

import com.netcracker.annotation.Attribute;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

import static com.netcracker.Constants.TRANSACTION_STATUS_NEW;
import static com.netcracker.Constants.transferRequestId;

public class TransferRequest {

    private int id = ++transferRequestId;

    @Attribute(value = 7)
    private int sender;

    @Attribute(value = 5)
    private long sum;

    @Attribute(value = 8)
    private int recipient;

    private Date date = new Date();

    private String status = TRANSACTION_STATUS_NEW;

    private String message = "";

    public TransferRequest() {
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSender() {
        return sender;
    }

    public long getSum() {
        return sum;
    }

    public int getRecipient() {
        return recipient;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "TransferRequest{" +
                "id=" + id +
                ", sender=" + sender +
                ", sum=" + sum +
                ", recipient=" + recipient +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
}
