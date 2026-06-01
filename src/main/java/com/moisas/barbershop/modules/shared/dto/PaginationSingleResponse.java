package com.moisas.barbershop.modules.shared.dto;

public record PaginationSingleResponse<T>(
        boolean success,
        T row
) {
    public PaginationSingleResponse(T data) {
        this(
                true,
                data
        );
    }
}