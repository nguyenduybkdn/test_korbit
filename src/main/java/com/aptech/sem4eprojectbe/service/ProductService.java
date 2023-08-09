package com.aptech.sem4eprojectbe.service;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.aptech.sem4eprojectbe.common.model.FilterCombine;
import com.aptech.sem4eprojectbe.common.model.IdModel;
import com.aptech.sem4eprojectbe.entity.ProductEntity;
import com.aptech.sem4eprojectbe.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @CacheEvict(value = "products", allEntries = true)
    public ProductEntity insertProduct(ProductEntity product) {
        System.out.println("Caching insert product ( delete cache : products)");
        return productRepository.save(product);
    }

    @Cacheable(value = "products")
    public List<ProductEntity> getAllProduct() {
        System.out.println("Caching get  products ( Create cache : product)");
        return productRepository.findByDeletedIsFalse();
    }

    @Cacheable(value = "product", key = "#idModel.getId()")
    public Optional<ProductEntity> getProductById(IdModel idModel) {
        System.out.println("Caching get product  ( Create cache : product and create key product saved)");
        return productRepository.findById(idModel.getId());
    }

    @Caching(evict = { @CacheEvict(value = "products", allEntries = true) }, put = {
            @CachePut(value = "product", key = "#product.getId()") })
    public ProductEntity updateProductEntity(ProductEntity product) {
        System.out.println(
                "Caching update product ( delete cache : products , and put and update key exits cache product)");

        return productRepository.findById(product.getId())
                .map(productItem -> {
                    if (!product.getImage().isEmpty()) {
                        productItem.setImage(product.getImage());
                    }
                    productItem.setName(product.getName());
                    productItem.setPrice(product.getPrice());
                    productItem.setDescription(product.getDescription());
                    productItem.setCategoryid(product.getCategoryid());
                    productItem.setAlcohol(product.getAlcohol());
                    productItem.setDeleted(product.getDeleted());
                    return productRepository.save(productItem);
                })
                .orElseGet(() -> {
                    ProductEntity newProduct = new ProductEntity();
                    newProduct.setName(product.getName());
                    newProduct.setImage(product.getImage());
                    newProduct.setPrice(product.getPrice());
                    newProduct.setDescription(product.getDescription());
                    newProduct.setCategoryid(product.getCategoryid());
                    newProduct.setAlcohol(product.getAlcohol());
                    newProduct.setDeleted(false);
                    return productRepository.save(newProduct);
                });
    }

    public List<ProductEntity> getAllByLeverAlcohol(String lever) {
        return productRepository.findByAlcoholLessThanEqual(lever);
    }

    public Optional<List<ProductEntity>> getAllByCategoryId(IdModel idModel) {
        return productRepository.findByCategoryid(idModel.getId());
    }

    public Optional<List<ProductEntity>> findByProductName(String productName) {
        String name = "%" + productName + "%";
        return productRepository.findByNameLikeIgnoreCaseAndDeletedIsFalse(name);
    }

    public List<ProductEntity> findByAlcoholAndCategoryId(FilterCombine alcohol) {
        return productRepository.findByAlcoholLessThanEqualAndCategoryid(alcohol.getAlcohol(), alcohol.getCateid());
    }
}
