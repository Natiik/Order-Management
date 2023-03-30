package com.order.ordermanagement.service;

import com.order.ordermanagement.exception.PermissionException;
import com.order.ordermanagement.object.User;
import com.order.ordermanagement.object.types.Authority;
import com.order.ordermanagement.object.types.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseUserPermissionService implements UserPermissionService {
    @Override
    public void checkPermissionForUser(User user,  UUID userId, Operation operation) {
        if (Authority.USER.equals(user.getAuthority())) {
            if (!userId.equals(user.getId())) {
                log.error("User [id: {}] with authority {} can't {} user with id {}",
                        user.getId(), user.getAuthority(), operation, userId);
                throw new PermissionException(operation.name(), "user");
            }
            return;
        } else if (Authority.MANAGER.equals(user.getAuthority())) {
            if (Operation.WRITE.equals(operation) || Operation.DELETE.equals(operation)) {
                if (!userId.equals(user.getId())) {
                    log.error("User [id: {}] with authority {} can't {} user with id {}",
                            user.getId(), user.getAuthority(), operation, userId);
                    throw new PermissionException(operation.name(), "user");
                }
                return;
            }
            return;
        }
        log.error("Unknown authority [{}] for user with id {}", user.getAuthority(), user.getId());
        throw new PermissionException(operation.name(), "user");
    }
}
