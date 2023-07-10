package com.blogapp.blog.app.exception;

import lombok.Data;

@Data
public class SameResourceFound extends RuntimeException{

    String message;
    public SameResourceFound(String message) {
        super(String.format("%s",message));
        this.message = message;
    }
}
