package com.eshop.catalog.infraestructure.entity;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "products")
public class ProductEntity {
    
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private int availableStock;

}