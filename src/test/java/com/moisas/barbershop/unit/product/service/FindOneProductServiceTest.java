package com.moisas.barbershop.unit.product.service;

import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.entity.ProductEntity;
import com.moisas.barbershop.modules.product.mapper.ProductMapper;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import com.moisas.barbershop.modules.product.service.FindOneProductService;
import com.moisas.barbershop.modules.shared.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class FindOneProductServiceTest {

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private FindOneProductService findOneProductService;

    @Test
    void shouldFindProductSuccessfully() {
        String productId = UUID.randomUUID().toString();
        ProductEntity entity = new ProductEntity();
        ProductDTO expectedDto = new ProductDTO();

        given(productRepository.findById(productId)).willReturn(Optional.of(entity));
        given(productMapper.toDTO(entity)).willReturn(expectedDto);

        ProductDTO result = findOneProductService.execute(productId);

        assertThat(result).isNotNull().isEqualTo(expectedDto);
        verify(productRepository).findById(productId);
        verify(productMapper).toDTO(entity);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        String productId = UUID.randomUUID().toString();

        given(productRepository.findById(productId)).willReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> findOneProductService.execute(productId));

        verify(productRepository).findById(productId);
        verifyNoInteractions(productMapper);
    }
}