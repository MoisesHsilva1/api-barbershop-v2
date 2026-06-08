package com.moisas.barbershop.modules.product.service;

import com.moisas.barbershop.modules.product.dto.FindAllProductDTO;
import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.entity.ProductEntity;
import com.moisas.barbershop.modules.product.mapper.ProductMapper;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FindAllProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Page<ProductDTO> execute(FindAllProductDTO params) {
        Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit());

        return fetchProduct(params, pageable).map(productMapper::toDTO);
    }

    private Page<ProductEntity> fetchProduct(FindAllProductDTO params, Pageable pageable) {
        return (params.getPrice() != null)
                ? productRepository.findAllByPrice(params.getPrice(), pageable)
                : productRepository.findAll(pageable);
    }
}
