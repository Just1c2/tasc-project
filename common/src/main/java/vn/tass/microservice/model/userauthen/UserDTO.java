package vn.tass.microservice.model.userauthen;

import lombok.Data;

@Data
public class UserDTO {
    private String token;
    private long userId;
}
