package com.tasc.userservice.connector;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.tass.microservice.model.BaseResponseV2;
import vn.tass.microservice.model.dto.product.ProductDTO;

@FeignClient(value = "product-service", url = "${tasc.product.address")
public interface ProductConnector {

    @GetMapping("/findByIdConnect")
    BaseResponseV2<ProductDTO> findByIdConnect(@RequestParam(name = "id") long id);
}
