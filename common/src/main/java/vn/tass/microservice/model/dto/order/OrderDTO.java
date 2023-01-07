package vn.tass.microservice.model.dto.order;

import java.io.Serializable;
import lombok.Data;

@Data
public class OrderDTO implements Serializable {
    private long orderId;
    private long productId;
    private long userId;
    private int total;
    private int status;
    private double totalPrice;

    public OrderDTO(){

    }
}
