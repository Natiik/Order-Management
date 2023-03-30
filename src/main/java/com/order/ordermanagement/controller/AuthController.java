package com.order.ordermanagement.controller;

import com.order.ordermanagement.controller.model.request.LoginRequest;
import com.order.ordermanagement.controller.model.request.RegistrationRequest;
import com.order.ordermanagement.security.JwtPair;
import com.order.ordermanagement.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController extends BaseController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            JwtPair login = authService.login(loginRequest);
            return ResponseEntity.ok(login);
        } catch (Exception e) {
            log.error("Failed to login user [email: {}]", loginRequest.email());
            return handleException(e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RegistrationRequest registrationRequest) {
        try {
            JwtPair jwt = authService.singUp(registrationRequest);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            log.error("Failed to create new user [email: {}]", registrationRequest.email());
            return handleException(e);
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {
        try {
            JwtPair token = authService.refreshToken(refreshToken);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to refresh token user [token: {}]", refreshToken);
            return handleException(e);
        }
    }
}
