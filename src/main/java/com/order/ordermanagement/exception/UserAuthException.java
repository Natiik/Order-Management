package com.order.ordermanagement.exception;

public class UserAuthException extends RuntimeException {
    public UserAuthException() {
        super("Can't authorize user");
    }
}
