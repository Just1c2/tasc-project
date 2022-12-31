package com.tasc.product.service;

import com.tasc.product.entity.CategoryEntity;
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
import vn.tass.microservice.model.dto.product.CategoryDTO;
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
        productEntity.setImage(request.getImage());
        productEntity.setCategory(request.getCategory());

        productRepository.save(productEntity);

        return new BaseResponseV2<>(productEntity);
    }

    public BaseResponseV2<ProductEntity> findById(long id) throws ApplicationException {
        Optional<ProductEntity> productOpt = productRepository.findById(id);

        if (productOpt.isEmpty()){
            throw new ApplicationException(ERROR.ID_NOT_FOUND);
        }

        return new BaseResponseV2<>(productOpt.get());
    }

    public BaseResponseV2<ProductEntity> findAll() throws ApplicationException {
        List<ProductEntity> productList = productRepository.findAll();

        if (productList.isEmpty()) {
            throw new ApplicationException(ERROR.RESULT_IS_NULL);
        }

        return new BaseResponseV2<>(productList);
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

    public BaseResponseV2<ProductDTO> findByIdForConnector(long id) throws ApplicationException {
        Optional<ProductEntity> productOpt = productRepository.findById(id);

        if (productOpt.isEmpty()){
            throw new ApplicationException(ERROR.ID_NOT_FOUND);
        }

        ProductDTO productDTO = new ProductDTO();
        ProductEntity product = productOpt.get();

        productDTO.setId(product.getId());
        productDTO.setName(productDTO.getName());
        productDTO.setImage(product.getImage());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());

        return new BaseResponseV2<>(productDTO);
    }
}
