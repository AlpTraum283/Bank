package com.netcracker.model.dto.rest;

import com.google.gson.Gson;
import com.netcracker.model.entity.TransferRequestEntity;
import com.netcracker.model.entity.UserEntity;

import java.util.List;

public class UserRequestsResponseDto {
    private UserEntity user;

    private List<TransferRequestEntity> requests;

    public UserRequestsResponseDto(UserEntity user, List<TransferRequestEntity> requests) {
        this.user = user;
        this.requests = requests;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<TransferRequestEntity> getRequests() {
        return requests;
    }

    public void setRequests(List<TransferRequestEntity> requests) {
        this.requests = requests;
    }

    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
