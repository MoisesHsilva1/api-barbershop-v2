package com.moisas.barbershop.unit.product.service;

import com.moisas.barbershop.modules.product.entity.ProductEntity;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import com.moisas.barbershop.modules.product.service.DeleteProductService;
import com.moisas.barbershop.modules.shared.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteProductServiceTest {

    @InjectMocks
    private DeleteProductService deleteProductService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void shouldDeleteProductSuccessfully() {
        String productId = UUID.randomUUID().toString();
        ProductEntity product = new ProductEntity();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        deleteProductService.execute(productId);

        ArgumentCaptor<ProductEntity> productCaptor = ArgumentCaptor.forClass(ProductEntity.class);
        verify(productRepository).save(productCaptor.capture());

        ProductEntity savedProduct = productCaptor.getValue();
        assertThat(savedProduct.getDeletedAt()).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        String productId = UUID.randomUUID().toString();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> deleteProductService.execute(productId));
    }
}