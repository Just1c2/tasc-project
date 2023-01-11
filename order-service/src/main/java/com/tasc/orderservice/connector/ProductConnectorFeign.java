package com.tasc.orderservice.connector;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.tass.microservice.model.BaseResponseV2;
import vn.tass.microservice.model.dto.product.ProductDTO;

@FeignClient(value = "product-connector", url = "${tasc.product.address}")
public interface ProductConnectorFeign {

    @GetMapping("/findByIdConnect")
    BaseResponseV2<ProductDTO> findByIdConnect(@RequestParam(name = "id") long id);
}
