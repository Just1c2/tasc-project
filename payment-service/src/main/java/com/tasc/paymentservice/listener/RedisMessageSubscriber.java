package com.tasc.paymentservice.listener;


import com.tasc.paymentservice.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import vn.tass.microservice.model.dto.order.OrderDTO;
import vn.tass.microservice.utils.JsonHelper;

@Log4j2
@Service
public class RedisMessageSubscriber implements MessageListener {

    @Autowired
    PaymentService paymentService;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = message.toString();

        OrderDTO orderDTO = JsonHelper.getObject(msg , OrderDTO.class);

        paymentService.handleEventOrder(orderDTO);

        log.info("PAYMENT-SERVICE order event created {}" , msg);
    }
}
