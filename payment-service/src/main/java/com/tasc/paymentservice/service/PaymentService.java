package com.tasc.paymentservice.service;

import com.tasc.paymentservice.entity.Balance;
import com.tasc.paymentservice.entity.PaymentEntity;
import com.tasc.paymentservice.listener.RedisMessageSubscriber;
import com.tasc.paymentservice.model.request.UpdatePaymentInfoRequest;
import com.tasc.paymentservice.repository.BalanceRepository;
import com.tasc.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import vn.tass.microservice.model.ApplicationException;
import vn.tass.microservice.model.BaseResponseV2;
import vn.tass.microservice.model.ERROR;
import vn.tass.microservice.model.constants.ORDER;
import vn.tass.microservice.model.constants.PAYMENT;
import vn.tass.microservice.model.dto.order.OrderDTO;
import vn.tass.microservice.model.userauthen.UserDTO;
import vn.tass.microservice.utils.JsonHelper;

import java.util.Optional;

@Service
public class PaymentService extends BaseService{

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    @Qualifier("order")
    ChannelTopic channelTopic;

    @Autowired
    RedisPusherMessageService redisPusherMessageService;

    public BaseResponseV2 updatePaymentInfo(UpdatePaymentInfoRequest request) throws ApplicationException {
        UserDTO userDTO = getUserDTO();

        PaymentEntity payment = new PaymentEntity();

        payment.setPaymentType(request.getPaymentType());
        payment.setUserId(userDTO.getUserId());
        payment.setStatus(PAYMENT.STATUS.ACTIVE);
        payment.setProvider(request.getProvider());
        payment.setExpiry(request.getExpiry());
        payment.setAccountNo(request.getAccountNo());

        paymentRepository.save(payment);

        return new BaseResponseV2<>();
    }

    public void handleEventOrder(OrderDTO orderDTO) {


        if (orderDTO.getStatus() == ORDER.STATUS.CREATED){
            this.handleOrderEventCreated(orderDTO);
            return;
        }

        if (orderDTO.getStatus() == ORDER.STATUS.SUCCESS){
            this.handleOrderEventSuccess(orderDTO);
            return;
        }
    }

    public void handleOrderEventCreated(OrderDTO orderDTO) throws ApplicationException{
        UserDTO userDTO = getUserDTO();

        Optional<Balance> balanceOpt = balanceRepository.findById(userDTO.getUserId());

        if (balanceOpt.isEmpty()) {
            orderDTO.setPaymentOrderStatus(ORDER.STATUS.FAIL);
            throw new ApplicationException(ERROR.USER_NOT_FOUND);
        }

        Balance balance = balanceOpt.get();

        if (orderDTO.getTotalPrice() > balance.getBalance()) {
            orderDTO.setPaymentOrderStatus(ORDER.STATUS.FAIL);
            throw new ApplicationException(ERROR.BALANCE_INSUFFICIENT);
        }

        orderDTO.setPaymentOrderStatus(ORDER.STATUS.SUCCESS);

        String message = JsonHelper.toString(orderDTO);

        redisPusherMessageService.sendMessage(message , channelTopic);


    }
    public void handleOrderEventSuccess(OrderDTO orderDTO) throws ApplicationException{
        UserDTO userDTO = getUserDTO();

        Optional<Balance> balanceOpt = balanceRepository.findById(userDTO.getUserId());

        if (balanceOpt.isEmpty()) {
            throw new ApplicationException(ERROR.USER_NOT_FOUND);
        }

        Balance balance = balanceOpt.get();

        balance.setBalance(balance.getBalance() - orderDTO.getTotalPrice());

    }

}
