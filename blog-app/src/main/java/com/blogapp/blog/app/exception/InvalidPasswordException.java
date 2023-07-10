package com.blogapp.blog.app.exception;

import lombok.Data;

@Data
public class InvalidPasswordException extends RuntimeException{
    String message;
    public InvalidPasswordException(String message) {
        super(String.format("%s",message));
        this.message = message;
    }
}
