package com.moisas.barbershop.modules.product.service;

import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.mapper.ProductMapper;
import com.moisas.barbershop.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindOneProductService {
    private final ProductMapper servicesMapper;
    private final ProductRepository productsRepository;

    @Transactional(readOnly = true)
    public Optional<ProductDTO> execute(String id) {
        return productsRepository.findById(id).map(servicesMapper::toDTO);
    }

}
