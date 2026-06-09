package com.moisas.barbershop.modules.product.service;

import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.mapper.ProductMapper;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import com.moisas.barbershop.modules.shared.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindOneProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductDTO execute(String id) {
        return productRepository.findById(id).map(productMapper::toDTO)
                .orElseThrow(ProductNotFoundException::new);
    }
}
