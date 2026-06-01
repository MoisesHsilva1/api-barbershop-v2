package com.moisas.barbershop.modules.product.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDTO {
    private String name;
    private  String description;
    private BigDecimal price;
    private LocalDateTime updateAt;
}
