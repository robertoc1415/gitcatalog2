package com.eshop.catalog.infraestructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
public class CatalogMockApiIntegrationTest {

    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Test
    public void testExternalApiCall() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        String mockResponse = "{ \"id\": \"32434\", \"name\": \"Product Name\", \"description\": \"Product Description\", \"price\": 100, \"availableStock\": 50 }";
        mockServer.expect(requestTo("http://external-api.com/products/32434"))
                .andRespond(withSuccess(mockResponse, MediaType.APPLICATION_JSON));

        String response = restTemplate.getForObject("http://external-api.com/products/32434", String.class);

        assertThat(response).isEqualTo(mockResponse);

        mockServer.verify();
    }
}