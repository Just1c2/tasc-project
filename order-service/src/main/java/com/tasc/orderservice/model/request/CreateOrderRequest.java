package com.tasc.orderservice.model.request;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private long productId;
    private int total;
}
