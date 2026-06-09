package com.moisas.barbershop.unit.product.service;

import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.dto.ProductRequestDTO;
import com.moisas.barbershop.modules.product.entity.ProductEntity;
import com.moisas.barbershop.modules.product.mapper.ProductMapper;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import com.moisas.barbershop.modules.product.service.UpdateProductService;
import com.moisas.barbershop.modules.shared.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateProductServiceTest {

    @InjectMocks
    private UpdateProductService updateProductService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Test
    void shouldUpdateProductSuccessfully() {
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("name")
                .description("description")
                .price(BigDecimal.valueOf(10.0))
                .build();

        String productId = UUID.randomUUID().toString();
        ProductEntity product = new ProductEntity();
        product.setId(productId);
        product.setName("old name");
        product.setDescription("old description");
        product.setPrice(BigDecimal.valueOf(5.0));

        ProductEntity updatedProduct = new ProductEntity();
        updatedProduct.setId(productId);
        updatedProduct.setName(productRequestDTO.getName());
        updatedProduct.setDescription(productRequestDTO.getDescription());
        updatedProduct.setPrice(productRequestDTO.getPrice());

        ProductDTO productDTO = ProductDTO.builder()
                .id(productId)
                .name("name")
                .description("description")
                .price(BigDecimal.valueOf(10.0))
                .build();

        when(productRepository.findByIdOrThrow(productId)).thenReturn(product);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(updatedProduct);
        when(productMapper.toDTO(updatedProduct)).thenReturn(productDTO);

        ProductDTO result = updateProductService.execute(productId, productRequestDTO);

        assertEquals(productDTO, result);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("name")
                .description("description")
                .price(BigDecimal.valueOf(10.0))
                .build();

        String productId = UUID.randomUUID().toString();

        when(productRepository.findByIdOrThrow(productId)).thenThrow(new ProductNotFoundException());

        assertThrows(ProductNotFoundException.class, () -> updateProductService.execute(productId, productRequestDTO));

        verify(productRepository, never()).save(any(ProductEntity.class));
    }
}