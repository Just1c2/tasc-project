package vn.tass.microservice.redis.dto;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Data
@RedisHash("login")
public class UserLoginDTO<T> {
    @Id
    private String token;

    private long userId;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private long timeToLive;

    private Set<T> role;
}
