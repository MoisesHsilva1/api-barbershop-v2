package com.moisas.barbershop.unit.product.service;

import com.moisas.barbershop.modules.product.dto.ProductRequestDTO;
import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.entity.ProductEntity;
import com.moisas.barbershop.modules.product.mapper.ProductMapper;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import com.moisas.barbershop.modules.product.service.CreateProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private CreateProductService createProductService;

    @Test
    void shouldCreateProductSuccessfully() {
        ProductRequestDTO createDto = new ProductRequestDTO();
        ProductEntity entity = new ProductEntity();
        ProductEntity savedEntity = new ProductEntity();
        ProductDTO expectedDto = new ProductDTO();

        given(productMapper.toEntity(createDto)).willReturn(entity);
        given(productRepository.save(entity)).willReturn(savedEntity);
        given(productMapper.toDTO(savedEntity)).willReturn(expectedDto);

        ProductDTO result = createProductService.execute(createDto);

        assertThat(result).isNotNull().isEqualTo(expectedDto);
        verify(productMapper).toEntity(createDto);
        verify(productRepository).save(entity);
        verify(productMapper).toDTO(savedEntity);
    }
}
