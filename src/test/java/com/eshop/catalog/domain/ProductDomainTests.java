package com.eshop.catalog.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.eshop.catalog.TestDataUtil;
import com.eshop.catalog.domain.model.Product;

@SpringBootTest
public class ProductDomainTests {
    
    @Test
	public void testThatProductIsValid() {
		Product product = TestDataUtil.validProduct();
		product.validateStockWhenCreating();
		assertEquals("Product 1", product.getName());
		assertEquals("Description 1", product.getDescription());
		assertEquals(new BigDecimal(100.00), product.getPrice());
		assertEquals(10, product.getAvailableStock());
	}

	@Test
	public void testThatProductIsInvalid() {
		Product product = TestDataUtil.invalidProduct();
		Exception exception = assertThrows(RuntimeException.class, product::validateStockWhenCreating);
		assertEquals("Stock cannot be zero", exception.getMessage());
	}
}
