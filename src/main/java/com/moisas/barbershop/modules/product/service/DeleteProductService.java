package com.moisas.barbershop.modules.product.service;

import com.moisas.barbershop.modules.product.entity.ProductEntity;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import com.moisas.barbershop.modules.shared.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class DeleteProductService {
    private final ProductRepository productRepository;

    public void execute(String id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        product.setDeletedAt(Instant.now());
        productRepository.save(product);
    }
}