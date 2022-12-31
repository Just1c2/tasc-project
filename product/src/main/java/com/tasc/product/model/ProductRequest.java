package com.tasc.product.model;

import com.tasc.product.entity.CategoryEntity;
import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private String description;
    private String image;
    private double price;
    private CategoryEntity category;
}
