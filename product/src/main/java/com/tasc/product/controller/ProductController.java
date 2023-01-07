package com.tasc.product.controller;

import com.tasc.product.model.ProductRequest;
import com.tasc.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.tass.microservice.model.ApplicationException;
import vn.tass.microservice.model.BaseResponseV2;
import vn.tass.microservice.model.dto.product.ProductDTO;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/findById")
    public BaseResponseV2 findById(@RequestParam(name = "id") Long id) throws ApplicationException {
        return productService.findById(id);
    }

    @GetMapping("/findAll")
    public BaseResponseV2 findAll() throws ApplicationException {
        return productService.findAll();
    }

    @PostMapping("/create")
    public BaseResponseV2 addProduct(@RequestBody ProductRequest request) throws ApplicationException {
        return productService.saveProduct(request);
    }

    @PutMapping("/update/{id}")
    public BaseResponseV2 updateProduct(@PathVariable(name = "id") Long id, @RequestBody ProductRequest request) throws ApplicationException {
        return productService.update(id, request);
    }

    @DeleteMapping("/delete")
    public BaseResponseV2 deleteProduct(@RequestParam(name = "id") Long id) throws ApplicationException{
        return productService.delete(id);
    }

    @GetMapping("/findByIdConnect")
    public BaseResponseV2<ProductDTO> findByIdConnect(@RequestParam(name = "id") Long id) throws ApplicationException {
        return productService.findByIdForConnector(id);
    }
}
