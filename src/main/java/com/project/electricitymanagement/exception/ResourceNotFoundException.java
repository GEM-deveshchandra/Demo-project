package com.project.electricitymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



/**
 * Exception thrown when a requested resource is not found.
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     * @param resourceName The name of the resource not found.
     * @param fieldName The name of the field that caused the resource not found.
     * @param fieldValue The value of the field that caused the resource not found.
     */
    public ResourceNotFoundException(final String resourceName, final String fieldName, final Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));


    }


}
