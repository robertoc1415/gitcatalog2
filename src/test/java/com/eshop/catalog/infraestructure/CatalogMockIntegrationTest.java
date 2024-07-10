package com.eshop.catalog.infraestructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.eshop.catalog.TestDataUtil;
import com.eshop.catalog.infraestructure.entity.ProductEntity;
import com.eshop.catalog.infraestructure.repository.ProductEntityRepository;
import com.eshop.catalog.infraestructure.services.impl.ProductEntityServicesImpl;

@ExtendWith(MockitoExtension.class)
public class CatalogMockIntegrationTest {
     
    @Mock
    private ProductEntityRepository productEntityRepository;

    @InjectMocks
    private ProductEntityServicesImpl productEntityServices;

    @Test
    public void testThatProductCanBeRetrievedById() {
        ProductEntity productEntity = ProductEntity.builder()
            .id("32434")
            .name(TestDataUtil.validProduct().getName())
            .description(TestDataUtil.validProduct().getDescription())
            .price(TestDataUtil.validProduct().getPrice())
            .availableStock(TestDataUtil.validProduct().getAvailableStock())
            .build();

        given(productEntityRepository.findById(productEntity.getId())).willReturn(Optional.of(productEntity));

        Optional<ProductEntity> returnedProduct = productEntityServices.getProductById(productEntity.getId());
        
        assertThat(returnedProduct).isPresent();
        assertThat(returnedProduct.get().getName()).isEqualTo(productEntity.getName());
        assertThat(returnedProduct.get().getDescription()).isEqualTo(productEntity.getDescription());
        assertThat(returnedProduct.get().getPrice()).isEqualTo(productEntity.getPrice());
        assertThat(returnedProduct.get().getAvailableStock()).isEqualTo(productEntity.getAvailableStock());
    }
}
