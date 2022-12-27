package com.tasc.product.model;

import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private String description;
    private String image;
    private double price;

}
