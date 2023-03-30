package com.order.ordermanagement.object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class UserCredentials {
    private UUID userId;
    private String password;
}
