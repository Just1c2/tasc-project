package com.tasc.orderservice.controller;

import com.tasc.orderservice.model.request.CreateOrderRequest;
import com.tasc.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.tass.microservice.customanotation.RequireUserLogin;
import vn.tass.microservice.model.ApplicationException;
import vn.tass.microservice.model.BaseResponse;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @PostMapping("/createOrder")
    @RequireUserLogin
    public BaseResponse createOrder(@RequestBody CreateOrderRequest request) throws ApplicationException {
        return orderService.createdOrder(request);
    }
}
