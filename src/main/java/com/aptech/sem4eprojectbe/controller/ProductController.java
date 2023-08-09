package com.aptech.sem4eprojectbe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.sem4eprojectbe.common.model.FilterCombine;
import com.aptech.sem4eprojectbe.common.model.IdModel;
import com.aptech.sem4eprojectbe.common.model.ResponseModel;
import com.aptech.sem4eprojectbe.entity.ProductEntity;
import com.aptech.sem4eprojectbe.entity.ProductNameEntity;
import com.aptech.sem4eprojectbe.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/insert-product")
    public ResponseModel insertProduct(@Valid @RequestBody ProductEntity product) {
        return new ResponseModel("ok", "success", productService.insertProduct(product));
    }

    @GetMapping("/products")
    public ResponseModel getAllProducts() {
        return new ResponseModel("ok", "success", productService.getAllProduct());
    }

    @PostMapping("/product")
    public ResponseModel getProductById(@Valid @RequestBody IdModel idModel) {
        Optional<ProductEntity> product = productService.getProductById(idModel);
        if (product.isPresent() && !product.get().getDeleted()) {
            return new ResponseModel("ok", "success", product);
        } else {
            return new ResponseModel("fail", "Can not find id : " + idModel.getId(), null);
        }
    }

    @PostMapping("/search-by-name")
    public ResponseModel getProductByName(@Valid @RequestBody ProductNameEntity name) {
        Optional<List<ProductEntity>> products = productService.findByProductName(name.getName());
        if (products.isPresent()) {
            return new ResponseModel("ok", "success", products);
        } else {
            return new ResponseModel("fail", "Can not find product by name: " + name.getName(), null);
        }
    }

    @PutMapping("/update-product")
    public ResponseModel updateProduct(@Valid @RequestBody ProductEntity product) {
        return new ResponseModel("ok", "success", productService.updateProductEntity(product));
    }

    @DeleteMapping("/delete-product")
    public ResponseModel deleteProduct(@Valid @RequestBody IdModel idModel) {
        Optional<ProductEntity> product = productService.getProductById(idModel);
        if (product.isPresent()) {
            ProductEntity updateProduct = product.get();
            updateProduct.setDeleted(true);
            productService.updateProductEntity(updateProduct);
            return new ResponseModel("ok", "success", null);
        } else {
            return new ResponseModel("fail", "Can not find id :" + idModel.getId(), null);
        }
    }

    @GetMapping("/alcohol")
    public ResponseModel getAllByLeverAlcohol(@Valid @RequestParam("alcoholNumber") String lever) {
        if (lever.equals("all")) {
            return new ResponseModel("ok", "success", productService.getAllProduct());
        }

        return new ResponseModel("ok", "success", productService.getAllByLeverAlcohol(lever));
    }

    @PostMapping("/product-by-cateId")
    public ResponseModel getProductByCategoryId(@Valid @RequestBody IdModel idModel) {
        if (idModel.getId().equals("all")) {
            return new ResponseModel("ok", "success", productService.getAllProduct());
        }
        Optional<List<ProductEntity>> product = productService.getAllByCategoryId(idModel);
        if (product.isPresent()) {
            return new ResponseModel("ok", "success", productService.getAllByCategoryId(idModel));
        }
        return null;
    }

    @PostMapping("/filter-combine")
    public ResponseModel getAllAlcoholAndCategoryId(@Valid @RequestBody FilterCombine filter) {
        List<ProductEntity> products = productService.findByAlcoholAndCategoryId(filter);

        // System.out.println("products " + products);
        return new ResponseModel("ok", "success", products);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel handleValidationException(MethodArgumentNotValidException exception) {
        return new ResponseModel("fail", "Validation Error", exception.getMessage());
    }

}
