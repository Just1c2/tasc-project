package com.tasc.orderservice.service;

import vn.tass.microservice.model.userauthen.UserDTO;
import vn.tass.microservice.utils.ThreadLocalCollection;

public class BaseService {

    public UserDTO getUserDTO() {
        return ThreadLocalCollection.getUserActionLog();
    }
}
