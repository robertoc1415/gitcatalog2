package com.eshop.catalog.api.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eshop.catalog.api.dto.ProductResponse;
import com.eshop.catalog.api.mappers.Mapper;
import com.eshop.catalog.domain.model.Product;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductToProductResponseImpl implements Mapper<Product, ProductResponse> {

    private final ModelMapper modelMapper;

    @Override
    public ProductResponse mapTo(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    public Product mapFrom(ProductResponse getProductResponse) {
        return modelMapper.map(getProductResponse, Product.class);
    }

}
