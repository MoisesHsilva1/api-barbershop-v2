package com.moisas.barbershop.modules.product.mapper;


import com.moisas.barbershop.modules.product.dto.CreateProductDTO;
import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public ProductEntity toEntity(CreateProductDTO dto) {
        ProductEntity model = new ProductEntity();

        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setPrice(dto.getPrice());
        model.setUpdatedAt(dto.getUpdateAt());

        return model;
    }

    public ProductDTO toDTO(ProductEntity model) {
        return ProductDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .price(model.getPrice())
                .updatedAt(model.getUpdatedAt())
                .deletedAt(model.getDeletedAt())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
