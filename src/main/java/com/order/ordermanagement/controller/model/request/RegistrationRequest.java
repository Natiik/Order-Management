package com.order.ordermanagement.controller.model.request;

import com.order.ordermanagement.object.types.Authority;

public record RegistrationRequest(String email, String password, Authority authority) {
}
