package com.tasc.userservice.model.request;

import lombok.Data;

@Data
public class UpdateInfoRequest {
    private String email;
    private int phone;
    private String address;

}
