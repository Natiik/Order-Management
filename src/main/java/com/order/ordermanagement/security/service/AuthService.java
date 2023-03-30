package com.order.ordermanagement.security.service;

import com.order.ordermanagement.controller.model.request.LoginRequest;
import com.order.ordermanagement.controller.model.request.RegistrationRequest;
import com.order.ordermanagement.security.JwtPair;

public interface AuthService {

    JwtPair login(LoginRequest loginRequest);

    JwtPair refreshToken(String oldRefreshToken);

    JwtPair singUp(RegistrationRequest registrationRequest);
}
