package com.tasc.paymentservice.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "balance")
public class Balance {

    @Id
    private long userId;

    private double balance;
}
