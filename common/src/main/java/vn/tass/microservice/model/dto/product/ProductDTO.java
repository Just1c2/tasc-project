package vn.tass.microservice.model.dto.product;

import lombok.Data;

@Data
public class ProductDTO {
    private long id;
    private String name;
    private String description;
    private String image;
    private double price;

}
