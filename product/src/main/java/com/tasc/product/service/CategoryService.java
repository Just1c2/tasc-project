package com.tasc.product.service;

import com.tasc.product.entity.CategoryEntity;
import com.tasc.product.entity.ProductEntity;
import com.tasc.product.model.CategoryRequest;
import com.tasc.product.repository.CategoryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.tass.microservice.model.ApplicationException;
import vn.tass.microservice.model.BaseResponseV2;
import vn.tass.microservice.model.ERROR;
import vn.tass.microservice.model.dto.product.CategoryDTO;
import vn.tass.microservice.model.dto.product.ProductDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public BaseResponseV2 saveCategory(CategoryRequest request) throws ApplicationException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ApplicationException(ERROR.CATEGORY_NAME_NULL);
        }

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(request.getName());
        categoryEntity.setDescription(request.getDescription());


        categoryRepository.save(categoryEntity);

        return new BaseResponseV2<>();
    }

    public BaseResponseV2<CategoryEntity> findById(long id) throws ApplicationException {
        Optional<CategoryEntity> categoryOpt = categoryRepository.findById(id);

        if (categoryOpt.isEmpty()) {
            throw new ApplicationException(ERROR.ID_NOT_FOUND);
        }

        return new BaseResponseV2<>(categoryOpt.get());
    }

    public BaseResponseV2<CategoryEntity> findAll() throws ApplicationException {
        List<CategoryEntity> categoryList = categoryRepository.findAll();

        if (categoryList.isEmpty()) {
            throw new ApplicationException(ERROR.RESULT_IS_NULL);
        }

        return new BaseResponseV2<>(categoryList);
    }

    public BaseResponseV2 delete(long id) throws ApplicationException {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            BaseResponseV2 responseV2 = new BaseResponseV2<>();
            responseV2.setMessage("Cannot delete this category due to it contains some products");
            responseV2.setCode(ERROR.THERE_ARE_PRODUCTS_IN_THIS_CATEGORY.code);

            return responseV2;
        }
        return new BaseResponseV2<>();
    }

    public BaseResponseV2 update(long id, CategoryRequest request) throws ApplicationException {
        Optional<CategoryEntity> categoryOpt = categoryRepository.findById(id);

        if (categoryOpt.isEmpty()) {
            throw new ApplicationException(ERROR.ID_NOT_FOUND);
        }

        CategoryEntity categoryEntity = categoryOpt.get();

        categoryEntity.setName(request.getName());
        categoryEntity.setDescription(request.getDescription());

        categoryRepository.save(categoryEntity);

        return new BaseResponseV2<>();
    }
}
