package com.tasc.warehouseservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "warehouse")
public class WarehouseEntity {
    @Id
    private long productId;

    private int qty;
}
