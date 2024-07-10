package com.eshop.catalog.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eshop.catalog.api.dto.CreateProduct;
import com.eshop.catalog.api.dto.ProductResponse;

public interface CatalogServices {
    
    public Page<ProductResponse> getAllProducts(Pageable pageable);

    public ProductResponse createProduct(CreateProduct createProduct);

    public Optional<ProductResponse> getProductById(String id);
    
}
