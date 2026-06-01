package com.moisas.barbershop.unit.product.service;


import com.moisas.barbershop.modules.product.dto.FindAllProductDTO;
import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.entity.ProductEntity;
import com.moisas.barbershop.modules.product.mapper.ProductMapper;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import com.moisas.barbershop.modules.product.service.FindAllProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllProductServiceTest {

    @InjectMocks
    private FindAllProductService findAllProductService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Test
    void shouldReturnAllProductsWhenNoPriceIsProvided() {
        FindAllProductDTO params = new FindAllProductDTO(0, 10, null);
        Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit());
        String uuid = UUID.randomUUID().toString();

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(uuid);
        productEntity.setName("Product 1");
        productEntity.setPrice(BigDecimal.TEN);

        Page<ProductEntity> productPage = new PageImpl<>(List.of(productEntity), pageable, 1);

        ProductDTO productDTO = ProductDTO.builder()
                .id(uuid)
                .name("Product 1")
                .price(BigDecimal.TEN)
                .build();

        when(productRepository.findAll(pageable)).thenReturn(productPage);
        when(productMapper.toDTO(any(ProductEntity.class))).thenReturn(productDTO);

        Page<ProductDTO> result = findAllProductService.execute(params);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Product 1");
    }

    @Test
    void shouldReturnProductsFilteredByPriceWhenPriceIsProvided() {
        BigDecimal price = BigDecimal.TEN;
        FindAllProductDTO params = new FindAllProductDTO(0, 10, price);
        Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit());
        String uuid = UUID.randomUUID().toString();

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(uuid);
        productEntity.setName("Product 1");
        productEntity.setPrice(price);

        Page<ProductEntity> productPage = new PageImpl<>(List.of(productEntity), pageable, 1);

        ProductDTO productDTO = ProductDTO.builder()
                .id(uuid)
                .name("Product 1")
                .price(price)
                .build();

        when(productRepository.findAllByPrice(price, pageable)).thenReturn(productPage);
        when(productMapper.toDTO(any(ProductEntity.class))).thenReturn(productDTO);

        Page<ProductDTO> result = findAllProductService.execute(params);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getPrice()).isEqualTo(price);
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsFound() {
        FindAllProductDTO params = new FindAllProductDTO(0, 10, null);
        Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit());

        when(productRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0));

        Page<ProductDTO> result = findAllProductService.execute(params);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
    }
}
