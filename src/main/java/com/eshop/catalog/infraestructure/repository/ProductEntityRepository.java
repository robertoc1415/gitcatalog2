package com.eshop.catalog.infraestructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eshop.catalog.infraestructure.entity.ProductEntity;

public interface ProductEntityRepository extends MongoRepository<ProductEntity, String> {
    
}
