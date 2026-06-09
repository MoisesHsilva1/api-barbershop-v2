package com.moisas.barbershop.modules.product.repository;

import com.moisas.barbershop.modules.product.entity.ProductEntity;
import com.moisas.barbershop.modules.shared.exceptions.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    default ProductEntity findByIdOrThrow(String id) {
        return findById(id).orElseThrow(ProductNotFoundException::new);
    }
    Page<ProductEntity> findAllByPrice(BigDecimal price, Pageable pageable);
}
