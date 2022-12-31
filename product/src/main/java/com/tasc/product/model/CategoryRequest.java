package com.tasc.product.model;

import com.tasc.product.entity.ProductEntity;
import lombok.Data;

import java.util.List;

@Data
public class CategoryRequest {

    private String name;
    private String description;
    private List<ProductEntity> products;
}
