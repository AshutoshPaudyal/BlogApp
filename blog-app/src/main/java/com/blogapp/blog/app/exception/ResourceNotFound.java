package com.blogapp.blog.app.exception;

import lombok.Data;

@Data
public class ResourceNotFound extends RuntimeException{
    String resourceName;
    String fieldName;
    Integer fieldValue;

    public ResourceNotFound(String resourceName, String fieldName, Integer fieldValue) {
        super(String.format("%s not found with %s: %d",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFound(String resourceName, String fieldName) {
        super(String.format("%s not found with %s",resourceName,fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }


}
