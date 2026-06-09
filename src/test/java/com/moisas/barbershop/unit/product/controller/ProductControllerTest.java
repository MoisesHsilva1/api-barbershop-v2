package com.moisas.barbershop.unit.product.controller;

import com.moisas.barbershop.modules.product.controller.ProductController;
import com.moisas.barbershop.modules.product.dto.FindAllProductDTO;
import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.dto.ProductRequestDTO;
import com.moisas.barbershop.modules.product.service.*;
import com.moisas.barbershop.modules.shared.dto.PaginationMultipleResponse;
import com.moisas.barbershop.modules.shared.dto.PaginationSingleResponse;
import com.moisas.barbershop.modules.shared.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private CreateProductService createProductService;

    @Mock
    private FindAllProductService findAllProductService;

    @Mock
    private FindOneProductService findOneProductService;

    @Mock
    private DeleteProductService deleteProductService;

    @Mock
    private UpdateProductService updateProductService;

    @InjectMocks
    private ProductController productController;

    @Test
    void shouldReturnCreatedWhenProductIsSuccessfullyCreated() {
        ProductRequestDTO createDto = ProductRequestDTO.builder().build();
        ProductDTO expectedDto = ProductDTO.builder().build();

        given(createProductService.execute(createDto)).willReturn(expectedDto);

        ResponseEntity<ProductDTO> response = productController.create(createDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull().isEqualTo(expectedDto);
        verify(createProductService).execute(createDto);
    }

    @Test
    void shouldReturnOkWithProductsWhenListingAll() {
        FindAllProductDTO params = new FindAllProductDTO();
        params.setOffset(0);
        params.setLimit(10);
        Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit());
        Page<ProductDTO> expectedPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        given(findAllProductService.execute(params)).willReturn(expectedPage);

        ResponseEntity<PaginationMultipleResponse<ProductDTO>> response = productController.listAll(params);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().rows()).isEmpty();
        verify(findAllProductService).execute(params);
    }

    @Test
    void shouldReturnOkWhenProductIsFound() {
        String productId = UUID.randomUUID().toString();
        ProductDTO expectedDto = ProductDTO.builder().build();

        given(findOneProductService.execute(productId)).willReturn(expectedDto);

        ResponseEntity<PaginationSingleResponse<ProductDTO>> response = productController.findById(productId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().row()).isEqualTo(expectedDto);
        verify(findOneProductService).execute(productId);
    }

    @Test
    void shouldThrowExceptionWhenFindingNonExistentProduct() {
        String productId = UUID.randomUUID().toString();

        when(findOneProductService.execute(productId)).thenThrow(new ProductNotFoundException());

        assertThrows(ProductNotFoundException.class, () -> productController.findById(productId));

        verify(findOneProductService).execute(productId);
    }

    @Test
    void shouldReturnOkWhenProductIsSuccessfullyUpdated() {
        String productId = UUID.randomUUID().toString();
        ProductRequestDTO updateDto = ProductRequestDTO.builder().build();
        ProductDTO expectedDto = ProductDTO.builder().build();

        given(updateProductService.execute(productId, updateDto)).willReturn(expectedDto);

        ResponseEntity<ProductDTO> response = productController.update(productId, updateDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isEqualTo(expectedDto);
        verify(updateProductService).execute(productId, updateDto);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        String productId = UUID.randomUUID().toString();
        ProductRequestDTO updateDto = ProductRequestDTO.builder().build();

        when(updateProductService.execute(productId, updateDto)).thenThrow(new ProductNotFoundException());

        assertThrows(ProductNotFoundException.class, () -> productController.update(productId, updateDto));

        verify(updateProductService).execute(productId, updateDto);
    }

    @Test
    void shouldReturnNoContentWhenProductIsSuccessfullyDeleted() {
        String productId = UUID.randomUUID().toString();

        willDoNothing().given(deleteProductService).execute(productId);

        ResponseEntity<Void> response = productController.delete(productId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        verify(deleteProductService).execute(productId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentProduct() {
        String productId = UUID.randomUUID().toString();

        doThrow(new ProductNotFoundException()).when(deleteProductService).execute(productId);

        assertThrows(ProductNotFoundException.class, () -> productController.delete(productId));

        verify(deleteProductService).execute(productId);
    }
}