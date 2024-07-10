package com.eshop.catalog.domain.model;

import java.math.BigDecimal;

public class Product {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private int availableStock;

    private Product() { }

    public void validateStockWhenCreating() {
        if (availableStock <= 0) {
            throw new RuntimeException("Stock cannot be zero");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private String description;
        private BigDecimal price;
        private int availableStock;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder availableStock(int availableStock) {
            this.availableStock = availableStock;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.id = this.id;
            product.name = this.name;
            product.description = this.description;
            product.price = this.price;
            product.availableStock = this.availableStock;
            return product;
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }
}