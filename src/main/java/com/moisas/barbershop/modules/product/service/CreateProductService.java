package com.moisas.barbershop.modules.product.service;

import com.moisas.barbershop.modules.product.dto.CreateProductDTO;
import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.entity.ProductEntity;
import com.moisas.barbershop.modules.product.mapper.ProductMapper;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Transactional
    public ProductDTO execute(CreateProductDTO service) {
        ProductEntity model = mapper.toEntity(service);

        return mapper.toDTO(productRepository.save(model));
    }
}
