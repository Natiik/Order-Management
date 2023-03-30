package com.order.ordermanagement.controller;

import com.order.ordermanagement.exception.InvalidJwtTokenException;
import com.order.ordermanagement.exception.LoginException;
import com.order.ordermanagement.exception.PermissionException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

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

        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
    }
}
