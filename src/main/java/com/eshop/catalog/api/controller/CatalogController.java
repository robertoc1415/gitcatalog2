package com.eshop.catalog.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.catalog.api.dto.CreateProduct;
import com.eshop.catalog.api.dto.ProductResponse;
import com.eshop.catalog.api.services.CatalogServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/catalog")  
public class CatalogController {

    private final CatalogServices catalogServices;

    @GetMapping
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return catalogServices.getAllProducts(pageable);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") String id) {
        Optional<ProductResponse> productResponse = catalogServices.getProductById(id);
        if (productResponse.isPresent()) {
            log.info("Product found: {}", productResponse.get());
            return ResponseEntity.ok(productResponse.get());
        } else {
            log.error("Product with id {} not found", id);
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Product with id " + id + " not found");
        }
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProduct createProduct) {
        return ResponseEntity.created(null).body(catalogServices.createProduct(createProduct));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        log.error("Error: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    
}
