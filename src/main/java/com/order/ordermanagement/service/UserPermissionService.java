package com.order.ordermanagement.service;

import com.order.ordermanagement.object.User;
import com.order.ordermanagement.object.types.Operation;

import java.util.UUID;

public interface UserPermissionService {
    void checkPermissionForUser(User user, UUID userId, Operation operation);
}
