package com.tasc.product.service;

import com.tasc.product.entity.ProductEntity;
import com.tasc.product.model.ProductRequest;
import com.tasc.product.repository.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.tass.microservice.model.ApplicationException;
import vn.tass.microservice.model.BaseResponseV2;
import vn.tass.microservice.model.ERROR;
import vn.tass.microservice.model.dto.product.ProductDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public BaseResponseV2 saveProduct(ProductRequest request) throws ApplicationException {

        if (StringUtils.isBlank(request.getName())) {
            throw new ApplicationException(ERROR.PRODUCT_NAME_NULL);
        }

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(request.getName());
        productEntity.setDescription(request.getDescription());
        productEntity.setPrice(request.getPrice());

        productRepository.save(productEntity);

        return new BaseResponseV2<>();
    }

    public BaseResponseV2<ProductDTO> findById(long id) throws ApplicationException {
        Optional<ProductEntity> productOpt = productRepository.findById(id);

        if (productOpt.isEmpty()){
            throw new ApplicationException(ERROR.ID_NOT_FOUND);
        }

        ProductDTO productDTO = new ProductDTO();

        ProductEntity productEntity = productOpt.get();

        BeanUtils.copyProperties(productEntity, productDTO);

        BaseResponseV2<ProductDTO> response = new BaseResponseV2<>();

        response.setData(productDTO);

        return response;
    }

    public BaseResponseV2<ProductDTO> findAll() throws ApplicationException {
        List<ProductEntity> productList = productRepository.findAll();

        if (productList.isEmpty()) {
            throw new ApplicationException(ERROR.RESULT_IS_NULL);
        }

        List<ProductDTO> productDTO = new ArrayList<>();

        for (ProductEntity product: productList
             ) {
            BeanUtils.copyProperties(product, productDTO);
        }

        BaseResponseV2<ProductDTO> response = new BaseResponseV2<>();
        response.setDataList(productDTO);

        return response;
    }

    public BaseResponseV2 delete(long id) throws ApplicationException {
        productRepository.deleteById(id);

        return new BaseResponseV2();
    }

    public BaseResponseV2 update(long id, ProductRequest request) throws ApplicationException {
        Optional<ProductEntity> productOpt = productRepository.findById(id);

        if (productOpt.isEmpty()) {
            throw new ApplicationException(ERROR.ID_NOT_FOUND);
        }

        ProductEntity productEntity = productOpt.get();

        productEntity.setName(request.getName());
        productEntity.setDescription(request.getDescription());
        productEntity.setImage(request.getImage());
        productEntity.setPrice(request.getPrice());

        productRepository.save(productEntity);

        return new BaseResponseV2<>();
    }
}
