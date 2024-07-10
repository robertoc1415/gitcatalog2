package com.eshop.catalog.infraestructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.eshop.catalog.TestDataUtil;
import com.eshop.catalog.domain.model.Product;
import com.eshop.catalog.infraestructure.entity.ProductEntity;
import com.eshop.catalog.infraestructure.services.ProductEntityServices;

@Testcontainers
@SpringBootTest
public class CatalogInfraestructureIntegrationTests {
    
    @Container
    final static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"))
        .withExposedPorts(27017);

    @BeforeAll
    static void beforeAll() {
        mongoDBContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
    
    @Autowired
    private ProductEntityServices productEntityServices;

    @Test
    public void testThatCreateProductIsSavedInDatabase() {
        Product product = TestDataUtil.validProduct();
        product.validateStockWhenCreating();

        ProductEntity productSaved = productEntityServices.createProduct(product);
        Optional<ProductEntity> productFound = productEntityServices.getProductById(productSaved.getId());

        assertTrue("Product with id " + productSaved.getId() + " not found", productFound.isPresent());
        assertEquals(productSaved.getId(), productFound.get().getId());
        assertEquals("Product 1", productFound.get().getName());
        assertEquals("Description 1", productFound.get().getDescription());
        assertEquals(new BigDecimal(100.00), productFound.get().getPrice());
        assertEquals(10, productFound.get().getAvailableStock());
    }
}
