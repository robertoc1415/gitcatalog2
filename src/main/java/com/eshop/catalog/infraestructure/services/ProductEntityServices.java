package com.eshop.catalog.infraestructure.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eshop.catalog.domain.model.Product;
import com.eshop.catalog.infraestructure.entity.ProductEntity;

public interface ProductEntityServices {
    
    public Page<ProductEntity> getAllProducts(Pageable pageable);

    public ProductEntity createProduct(Product product);

    public Optional<ProductEntity> getProductById(String id);
    
}
