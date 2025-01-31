package com.guru.userManagementSystem.security.exception;

public class UserCreateForbiddenException extends RuntimeException{

    public UserCreateForbiddenException(String message){
        super(message);
    }
}
