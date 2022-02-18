package com.phoenix.phoenix.web.exceptions;

public class ProductDoesNotExistException extends BusinessLogicException {
    public ProductDoesNotExistException(String message) {
        super(message);
    }
}
