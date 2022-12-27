package vn.tass.microservice.redis.repository;


import org.springframework.data.repository.CrudRepository;
import vn.tass.microservice.redis.dto.UserLoginDTO;

public interface UserLoginRepository extends CrudRepository<UserLoginDTO, String> {

}
