package com.eshop.catalog.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.eshop.catalog.TestDataUtil;
import com.eshop.catalog.api.dto.CreateProduct;
import com.eshop.catalog.api.dto.ProductResponse;
import com.eshop.catalog.api.mappers.Mapper;
import com.eshop.catalog.domain.model.Product;
import com.eshop.catalog.infraestructure.entity.ProductEntity;
import com.eshop.catalog.infraestructure.services.ProductEntityServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.closeTo;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class CatalogControllerIntegrationTests {
    
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
    private MockMvc mockMvc;
    @Autowired
    private Mapper<ProductEntity, Product> productEntityToProductMapper;
    @Autowired
    private Mapper<Product, ProductResponse> productToProductResponseMapper;
    @Autowired
    private ProductEntityServices productEntityServices;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testThatCreateProductReturnsHttp201Created() throws JsonProcessingException, Exception {
        CreateProduct createProduct = TestDataUtil.createProduct();

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/catalog")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProduct)))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(createProduct.getName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.description", is(createProduct.getDescription())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.availableStock", is(createProduct.getAvailableStock())));
    }
    
    @Test
    public void testThatGetProductReturnsHttp200Ok() throws Exception {
        CreateProduct createProduct = TestDataUtil.createProduct();
        Product product = Product.builder()
            .name(createProduct.getName())
            .description(createProduct.getDescription())
            .price(createProduct.getPrice())
            .availableStock(createProduct.getAvailableStock())
            .build();
     
        ProductResponse productResponse =  productToProductResponseMapper
            .mapTo(productEntityToProductMapper
                .mapTo(productEntityServices.createProduct(product)));
      mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/catalog/" + productResponse.getId())
                .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(
                MockMvcResultMatchers.status().isOk()
            );
    }
}