package com.tasc.paymentservice.model.request;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UpdatePaymentInfoRequest {
    private String paymentType;
    private String provider;
    private long accountNo;
    private Date expiry;
}
