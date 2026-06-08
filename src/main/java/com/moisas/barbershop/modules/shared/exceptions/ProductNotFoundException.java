package com.moisas.barbershop.modules.shared.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("Produto não encontrado.");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}