package com.eshop.catalog.api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateProduct {
    
    private String name;
    private String description;
    private BigDecimal price;
    private int availableStock;
    
}
