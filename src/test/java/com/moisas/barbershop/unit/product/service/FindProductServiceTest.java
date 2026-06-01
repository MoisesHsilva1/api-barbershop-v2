package com.moisas.barbershop.unit.product.service;

import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.entity.ProductEntity;
import com.moisas.barbershop.modules.product.mapper.ProductMapper;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import com.moisas.barbershop.modules.product.service.FindOneProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class FindProductServiceTest {

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private FindOneProductService findOneProductService;

    @Test
    void shouldFindProductSuccessfully() {
        String serviceId = "service-123";
        ProductEntity entity = new ProductEntity();
        ProductDTO expectedDto = new ProductDTO();

        given(productRepository.findById(serviceId)).willReturn(Optional.of(entity));
        given(productMapper.toDTO(entity)).willReturn(expectedDto);

        Optional<ProductDTO> result = findOneProductService.execute(serviceId);

        assertThat(result).isPresent().contains(expectedDto);
    }

    @Test
    void shouldReturnEmptyWhenProductNotFound() {
        String serviceId = "service-123";

        given(productRepository.findById(serviceId)).willReturn(Optional.empty());

        Optional<ProductDTO> result = findOneProductService.execute(serviceId);

        assertThat(result).isEmpty();
        verifyNoInteractions(productMapper);
    }
}
