package com.order.ordermanagement.exception;

public class PermissionException extends RuntimeException {
    public PermissionException(String operation, String entityType) {
        super(String.format("Permission denied to read %s %s", operation, entityType));
    }
}
