package com.tasc.warehouseservice.service;

import com.tasc.warehouseservice.connector.ProductConnector;
import com.tasc.warehouseservice.entity.WarehouseEntity;
import com.tasc.warehouseservice.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import vn.tass.microservice.model.ApplicationException;
import vn.tass.microservice.model.BaseResponseV2;
import vn.tass.microservice.model.ERROR;
import vn.tass.microservice.model.constants.ORDER;
import vn.tass.microservice.model.dto.order.OrderDTO;
import vn.tass.microservice.model.dto.product.ProductDTO;
import vn.tass.microservice.utils.JsonHelper;

import java.util.Optional;

@Service
public class WarehouseService extends BaseService{

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    ProductConnector productConnector;

    @Autowired
    @Qualifier("order")
    ChannelTopic channelTopic;

    @Autowired
    RedisPusherMessageService redisPusherMessageService;

    public void handleEventOrder(OrderDTO orderDTO ) {
        if (orderDTO.getStatus() == ORDER.STATUS.CREATED){
            this.handleOrderEventCreated(orderDTO);
            return;
        }

        if (orderDTO.getStatus() == ORDER.STATUS.SUCCESS){
            this.handleOrderEventSuccess(orderDTO);
            return;
        }
    }

    public void handleOrderEventCreated(OrderDTO orderDTO) throws ApplicationException {

        BaseResponseV2<ProductDTO> productInfoResponse = productConnector.findByIdConnect(orderDTO.getProductId());

        if (!productInfoResponse.isSuccess()) {
            orderDTO.setWarehouseOrderStatus(ORDER.STATUS.FAIL);
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        ProductDTO productDTO = productInfoResponse.getData();

        if (productDTO == null) {
            orderDTO.setWarehouseOrderStatus(ORDER.STATUS.FAIL);
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        Optional<WarehouseEntity> warehouseOpt = warehouseRepository.findById(orderDTO.getProductId());

        if (warehouseOpt.isEmpty()) {
            orderDTO.setWarehouseOrderStatus(ORDER.STATUS.FAIL);
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        orderDTO.setWarehouseOrderStatus(ORDER.STATUS.SUCCESS);

        String message = JsonHelper.toString(orderDTO);

        redisPusherMessageService.sendMessage(message , channelTopic);

    }
    public void handleOrderEventSuccess(OrderDTO orderDTO) throws ApplicationException {

        Optional<WarehouseEntity> warehouseOpt = warehouseRepository.findById(orderDTO.getProductId());

        if (warehouseOpt.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        WarehouseEntity warehouse = warehouseOpt.get();

        warehouse.setQty(warehouse.getQty() - orderDTO.getTotal());

    }

}
