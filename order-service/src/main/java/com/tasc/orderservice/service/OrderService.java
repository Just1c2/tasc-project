package com.tasc.orderservice.service;

import com.tasc.orderservice.connector.ProductConnector;
import com.tasc.orderservice.entity.OrderEntity;
import com.tasc.orderservice.model.request.CreateOrderRequest;
import com.tasc.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import vn.tass.microservice.model.ApplicationException;
import vn.tass.microservice.model.BaseResponse;
import vn.tass.microservice.model.BaseResponseV2;
import vn.tass.microservice.model.ERROR;
import vn.tass.microservice.model.constants.ORDER;
import vn.tass.microservice.model.dto.order.OrderDTO;
import vn.tass.microservice.model.dto.product.ProductDTO;
import vn.tass.microservice.model.userauthen.UserDTO;
import vn.tass.microservice.utils.JsonHelper;

@Service
public class OrderService extends BaseService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductConnector productConnector;

    @Autowired
    @Qualifier("order")
    ChannelTopic channelTopic;

    @Autowired
    RedisPusherMessageService redisPusherMessageService;

    public BaseResponse createdOrder(CreateOrderRequest request) throws ApplicationException {
        UserDTO userDTO = getUserDTO();

        if (request.getProductId() < 1 || request.getTotal() < 1) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        BaseResponseV2<ProductDTO> productInfoResponse = productConnector.findByIdConnect(request.getProductId());

        if (!productInfoResponse.isSuccess()) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        ProductDTO productDTO = productInfoResponse.getData();

        if (productDTO == null) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        OrderEntity order = new OrderEntity();

        order.setIsSuccess(ORDER.SUCCESS_STATUS.FAIL);
        order.setUserId(userDTO.getUserId());
        order.setStatus(ORDER.STATUS.CREATED);
        order.setProductId(request.getProductId());
        order.setTotalItems(request.getTotal());

        orderRepository.save(order);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setProductId(order.getProductId());
        orderDTO.setTotal(order.getTotalItems());
        orderDTO.setTotalPrice(order.getTotalItems() * productDTO.getPrice());


        //send message

        String message = JsonHelper.toString(orderDTO);

        redisPusherMessageService.sendMessage(message, channelTopic);

        return new BaseResponse();

    }

}
