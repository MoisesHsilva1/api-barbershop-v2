package com.moisas.barbershop.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindAllProductDTO {
    private Integer offset = 0;
    private Integer limit = 10;
    private BigDecimal price;
}
