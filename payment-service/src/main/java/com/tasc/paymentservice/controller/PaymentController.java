package com.tasc.paymentservice.controller;

import com.tasc.paymentservice.model.request.UpdatePaymentInfoRequest;
import com.tasc.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.tass.microservice.model.ApplicationException;
import vn.tass.microservice.model.BaseResponseV2;

@RestController
@RequestMapping
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/updateInfo")
    public BaseResponseV2 updatePaymentInfo(@RequestBody UpdatePaymentInfoRequest request) throws ApplicationException {
        return paymentService.updatePaymentInfo(request);
    }
}
