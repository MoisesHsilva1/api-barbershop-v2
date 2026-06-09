package com.moisas.barbershop.modules.product.service;

import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.dto.ProductRequestDTO;
import com.moisas.barbershop.modules.product.entity.ProductEntity;
import com.moisas.barbershop.modules.product.mapper.ProductMapper;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UpdateProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDTO execute(String id, ProductRequestDTO dto) {
        ProductEntity product = productRepository.findByIdOrThrow(id);

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setUpdatedAt(Instant.now());

        ProductEntity updatedProduct = productRepository.save(product);

        return productMapper.toDTO(updatedProduct);
    }
}
