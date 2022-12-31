package com.tasc.product.controller;

import com.tasc.product.model.CategoryRequest;
import com.tasc.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.tass.microservice.model.ApplicationException;
import vn.tass.microservice.model.BaseResponseV2;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/findById")
    public BaseResponseV2 findById(@RequestParam(name = "id") Long id) throws ApplicationException {
        return categoryService.findById(id);
    }

    @GetMapping("/findAll")
    public BaseResponseV2 findAll() throws ApplicationException{
        return categoryService.findAll();
    }

    @PostMapping("/create")
    public BaseResponseV2 addCategory(@RequestBody CategoryRequest request) throws ApplicationException {
        return categoryService.saveCategory(request);
    }

    @PutMapping("/update")
    public BaseResponseV2 updateCategory(@RequestParam(name = "id") Long id, @RequestBody CategoryRequest request) throws ApplicationException {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/delete")
    public BaseResponseV2 deleteCategory(@RequestParam(name = "id") Long id) throws ApplicationException {
        return categoryService.delete(id);
    }
}
