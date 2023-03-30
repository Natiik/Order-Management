package com.order.ordermanagement.service;

import com.order.ordermanagement.object.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User save(User securityUser, User user);

    Optional<User> findById(User securityUser,UUID id);

    Optional<User> findByEmail(User securityUser,String email);

    void delete(User securityUser,UUID id);
}
