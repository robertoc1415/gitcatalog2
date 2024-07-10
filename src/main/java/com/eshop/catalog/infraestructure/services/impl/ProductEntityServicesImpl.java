package com.eshop.catalog.infraestructure.services.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.eshop.catalog.domain.model.Product;
import com.eshop.catalog.infraestructure.entity.ProductEntity;
import com.eshop.catalog.infraestructure.repository.ProductEntityRepository;
import com.eshop.catalog.infraestructure.services.ProductEntityServices;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductEntityServicesImpl implements ProductEntityServices {

    private final ProductEntityRepository productEntityRepository;

    @Override
    public Page<ProductEntity> getAllProducts(Pageable pageable) {
        return productEntityRepository.findAll(pageable);
    }

    @Override
    public ProductEntity createProduct(Product product) {
        return productEntityRepository.save(ProductEntity.builder()
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .availableStock(product.getAvailableStock())
            .build());
    }

    @Override
    public Optional<ProductEntity> getProductById(String id) {
        return productEntityRepository.findById(id);
    }   
    
}
