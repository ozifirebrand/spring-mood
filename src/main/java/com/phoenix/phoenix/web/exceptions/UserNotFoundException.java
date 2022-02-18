package com.phoenix.phoenix.web.exceptions;

public class UserNotFoundException extends BusinessLogicException{
    public UserNotFoundException(String message){
        super(message);
    }
}
