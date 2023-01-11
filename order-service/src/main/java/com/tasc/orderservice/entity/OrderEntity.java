package com.tasc.orderservice.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    private long productId;

    @Column(name = "is_success")
    private int isSuccess;

    private int status;
    private String errorCode;
    private int totalItems;
    private double totalPrice;
}
