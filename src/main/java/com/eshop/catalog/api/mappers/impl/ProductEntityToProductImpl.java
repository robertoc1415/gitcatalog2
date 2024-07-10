package com.eshop.catalog.api.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.eshop.catalog.api.mappers.Mapper;
import com.eshop.catalog.domain.model.Product;
import com.eshop.catalog.infraestructure.entity.ProductEntity;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductEntityToProductImpl implements Mapper<ProductEntity, Product>{

    private final ModelMapper modelMapper;

    @Override
    public Product mapTo(ProductEntity productEntity) {
        return modelMapper.map(productEntity, Product.class);
    }

    @Override
    public ProductEntity mapFrom(Product product) {
        return modelMapper.map(product, ProductEntity.class);
    }
    
}
