package com.order.ordermanagement.security.service;

import com.order.ordermanagement.controller.model.request.LoginRequest;
import com.order.ordermanagement.controller.model.request.RegistrationRequest;
import com.order.ordermanagement.exception.InvalidJwtTokenException;
import com.order.ordermanagement.exception.LoginException;
import com.order.ordermanagement.security.JwtPair;
import com.order.ordermanagement.util.JwtUtils;
import com.order.ordermanagement.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class BaseAuthService implements AuthService {
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public JwtPair login(LoginRequest loginRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.email());
        if (userDetails.getPassword().equals(loginRequest.email())) {
            log.error("Trying to login user [email: {}] with illegal password", loginRequest.email());
            throw new LoginException("Illegal email or password");
        }

        String jwtToken = jwtUtils.generateToken(userDetails, new HashMap<>());
        String refreshToken = jwtUtils.generateRefreshToken(userDetails, new HashMap<>());
        return new JwtPair(jwtToken, refreshToken);
    }

    @Override
    public JwtPair refreshToken(String oldRefreshToken) {
        String userId = jwtUtils.extractUsername(oldRefreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        if (jwtUtils.isTokenValid(oldRefreshToken, userDetails)) {
            String jwtToken = jwtUtils.generateToken(userDetails, new HashMap<>());
            String refreshToken = jwtUtils.generateRefreshToken(userDetails, new HashMap<>());
            return new JwtPair(jwtToken, refreshToken);
        }
        log.error("Provided refresh token is invalid for user {}", userId);
        throw new InvalidJwtTokenException();
    }

    @Override
    public JwtPair singUp(RegistrationRequest registrationRequest) {
        ValidateUtil.validateEmail(registrationRequest.email());
        ValidateUtil.validatePassword(registrationRequest.password());

        try {
            UserDetails newUser = userDetailsService.createNewUser(registrationRequest);
            String jwtToken = jwtUtils.generateToken(newUser, new HashMap<>());
            String refreshToken = jwtUtils.generateRefreshToken(newUser, new HashMap<>());
            return new JwtPair(jwtToken, refreshToken);
        } catch (Throwable e) {
            if (e.getCause() != null && e.getCause().getCause().getMessage() != null &&
                    e.getCause().getCause().getMessage().contains("already exists")) {
                throw new IllegalArgumentException("User with email already exists");
            }
            throw new RuntimeException(e.getMessage());
        }
    }
}
