package com.order.ordermanagement.service;

import com.order.ordermanagement.dao.entity.UserEntity;
import com.order.ordermanagement.dao.service.UserDaoService;
import com.order.ordermanagement.object.User;
import com.order.ordermanagement.object.types.Operation;
import com.order.ordermanagement.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseUserService implements UserService {
    private PasswordEncoder encoder;
    private final UserDaoService userDaoService;
    private final UserPermissionService userPermissionService;

    @Override
    public User save(User currentUser, User user) {
        validateUser(user);
        userPermissionService.checkPermissionForUser(currentUser, user.getId(), Operation.WRITE);
        UserEntity saved = userDaoService.save(user.toEntity());
        return saved.toData();
    }

    @Override
    public Optional<User> findById(User securityUser, UUID id) {
        Optional<UserEntity> byId = userDaoService.findById(id);
        return byId.map(UserEntity::toData);
    }

    @Override
    public Optional<User> findByEmail(User securityUser, String email) {
        Optional<UserEntity> byEmail = userDaoService.findByEmail(email);
        return byEmail.map(UserEntity::toData);
    }

    @Override
    public void delete(User securityUser, UUID toDelete) {
        userDaoService.delete(toDelete);
    }

    private void validateUser(User user) {
        if (user == null) {
            log.error("User is null");
            throw new IllegalArgumentException("User can't be null");
        }

        if (user.getId() == null) {
            log.error("UserId is null");
            throw new IllegalArgumentException("UserId can't be null");
        }
        ValidateUtil.validateEmail(user.getEmail());
    }
}
