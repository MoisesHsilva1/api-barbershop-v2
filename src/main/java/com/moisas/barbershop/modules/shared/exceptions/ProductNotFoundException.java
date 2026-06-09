package com.moisas.barbershop.modules.shared.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("Product not found.");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}