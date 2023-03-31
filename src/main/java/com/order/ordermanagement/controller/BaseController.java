package com.order.ordermanagement.controller;

import com.order.ordermanagement.exception.InvalidJwtTokenException;
import com.order.ordermanagement.exception.LoginException;
import com.order.ordermanagement.exception.PermissionException;
import com.order.ordermanagement.exception.UserAuthException;
import com.order.ordermanagement.object.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.management.OperationsException;
import java.util.NoSuchElementException;

public class BaseController {
    ResponseEntity<?> handleException(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }

        if (e instanceof InvalidJwtTokenException) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(4031));
        }

        if (e instanceof LoginException) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(401));
        }

        if (e instanceof PermissionException) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(403));
        }

        if (e instanceof NoSuchElementException) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        }

        if (e instanceof OperationsException) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(403));
        }

        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
    }

    User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UserAuthException();
        }
        return (User) authentication.getPrincipal();
    }
}
