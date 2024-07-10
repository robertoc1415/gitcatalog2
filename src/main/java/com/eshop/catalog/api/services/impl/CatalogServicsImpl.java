package com.eshop.catalog.api.services.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eshop.catalog.api.dto.CreateProduct;
import com.eshop.catalog.api.dto.ProductResponse;
import com.eshop.catalog.api.mappers.Mapper;
import com.eshop.catalog.api.services.CatalogServices;
import com.eshop.catalog.domain.model.Product;
import com.eshop.catalog.infraestructure.entity.ProductEntity;
import com.eshop.catalog.infraestructure.services.ProductEntityServices;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CatalogServicsImpl implements CatalogServices {

    private final ProductEntityServices productEntityServices;
    private final Mapper<ProductEntity, Product> productEntityToProductMapper;
    private final Mapper<Product, ProductResponse> productToProductResponseMapper;

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        
        Page<ProductEntity> productEntities = productEntityServices.getAllProducts(pageable);
        Page<Product> products = productEntities
            .map(productEntityToProductMapper::mapTo);
        return products
            .map(productToProductResponseMapper::mapTo);
    }

    @Override
    public ProductResponse createProduct(CreateProduct createProduct) {
            log.info("Create product request started with information {}", createProduct);
            Product product = Product.builder()
                .name(createProduct.getName())
                .description(createProduct.getDescription())
                .price(new BigDecimal(createProduct.getPrice().toString()))
                .availableStock(createProduct.getAvailableStock())
                .build();

            product.validateStockWhenCreating();
                
            ProductEntity productEntity = productEntityServices.createProduct(product);

            log.info("The product was created: {}", productEntity.toString());
            
            return productToProductResponseMapper
                .mapTo(productEntityToProductMapper
                    .mapTo(productEntity));  
    }

    @Override
    public Optional<ProductResponse> getProductById(String id) {
        Optional<ProductEntity> productEntity = productEntityServices.getProductById(id);
        return productEntity
            .map(productEntityToProductMapper::mapTo)
            .map(productToProductResponseMapper::mapTo);
    }
    
}
