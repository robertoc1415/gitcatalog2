package com.eshop.catalog;

import java.math.BigDecimal;

import com.eshop.catalog.api.dto.CreateProduct;
import com.eshop.catalog.domain.model.Product;

public class TestDataUtil {
    
    private TestDataUtil() {
    }

    public static final Product validProduct() {
        return Product.builder()
            .name("Product 1")
            .description("Description 1")
            .price(new BigDecimal(100.00))
            .availableStock(10)
            .build();
    }

    public static final Product invalidProduct() {
        return Product.builder()
            .name("Product 2")
            .description("Description 2")
            .price(new BigDecimal(100.00))
            .availableStock(-1)
            .build();
    }
    
    public static final CreateProduct createProduct() {
        return CreateProduct.builder()
            .name("Product 3")
            .description("Description 3")
            .price(new BigDecimal(100.00))
            .availableStock(10)
            .build();
    }
}
