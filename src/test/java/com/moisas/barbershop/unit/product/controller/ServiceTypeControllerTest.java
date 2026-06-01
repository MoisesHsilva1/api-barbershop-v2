package com.moisas.barbershop.unit.product.controller;

import com.moisas.barbershop.modules.product.controller.ProductController;
import com.moisas.barbershop.modules.product.dto.CreateProductDTO;
import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.service.CreateProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ServiceTypeControllerTest {

    @Mock
    private CreateProductService createServices;

    @InjectMocks
    private ProductController servicesController;

    @Test
    void shouldReturnCreatedWhenServiceIsSuccessfullyCreated() {
        CreateProductDTO createDto = new CreateProductDTO();
        ProductDTO expectedDto = new ProductDTO();

        given(createServices.execute(createDto)).willReturn(expectedDto);

        ResponseEntity<ProductDTO> response = servicesController.create(createDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull().isEqualTo(expectedDto);
    }
}
