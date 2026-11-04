package com.smartwaste.exception;

/**
 * ResourceNotFoundException
 * 
 * Domain exception thrown whenever a requested resource (such as a WasteBin,
 * User, CollectionRoute, or Alert) cannot be located in the database.
 * 
 * Caught and formatted into an HTTP 404 response by GlobalExceptionHandler.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
