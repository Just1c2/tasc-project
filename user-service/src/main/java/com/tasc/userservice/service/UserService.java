package com.tasc.userservice.service;

import com.tasc.userservice.entity.UserEntity;
import com.tasc.userservice.model.request.LoginRequest;
import com.tasc.userservice.model.request.RegisterRequest;
import com.tasc.userservice.model.request.UpdateInfoRequest;
import com.tasc.userservice.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vn.tass.microservice.model.ApplicationException;
import vn.tass.microservice.model.BaseResponseV2;
import vn.tass.microservice.model.ERROR;
import vn.tass.microservice.redis.dto.UserLoginDTO;
import vn.tass.microservice.redis.repository.UserLoginRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserLoginRepository userLoginRepository;

    public BaseResponseV2 register(RegisterRequest request) throws ApplicationException {
        if (StringUtils.isBlank(request.getUsername())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Username is empty");
        }

        if (StringUtils.isBlank(request.getPassword())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Password is empty");
        }

        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Password not match");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));


        userRepository.save(user);

        return new BaseResponseV2<>(user);
    }

    public BaseResponseV2<UserLoginDTO> login(LoginRequest request) throws ApplicationException {
        Optional<UserEntity> userOpt = userRepository.findUserEntityByUsername(request.getUsername());

        if (userOpt.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Username not found");
        }

        UserEntity user = userOpt.get();

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Password not matched");
        }

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        String token = UUID.randomUUID().toString();

        userLoginDTO.setToken(token);
        userLoginDTO.setUserId(user.getId());
        userLoginDTO.setTimeToLive(20000);
        userLoginDTO.setRole(user.getRoles());

        userLoginRepository.save(userLoginDTO);

        BaseResponseV2<UserLoginDTO> loginResponse = new BaseResponseV2<>();
        loginResponse.setData(userLoginDTO);

        return loginResponse;
    }

    public BaseResponseV2 updateInfo(UpdateInfoRequest request, long id) throws ApplicationException {
        Optional<UserEntity> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new ApplicationException(ERROR.ID_NOT_FOUND, "Id not found");
        }

        UserEntity user = userOpt.get();

        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        userRepository.save(user);

        return new BaseResponseV2<>();
    }
}
