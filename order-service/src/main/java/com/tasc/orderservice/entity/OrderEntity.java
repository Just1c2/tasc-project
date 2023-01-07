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

    @Column(name = "user-id")
    private long userId;
    @Column(name = "product-id")
    private long productId;

    @Column(name = "is-success")
    private int isSuccess;

    private int status;
    private String errorCode;
    private int totalItems;
}
