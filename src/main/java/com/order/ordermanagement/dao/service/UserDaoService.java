package com.order.ordermanagement.dao.service;

import com.order.ordermanagement.dao.entity.UserEntity;
import com.order.ordermanagement.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDaoService {
    private final UserRepository userRepository;

    public UserEntity save(UserEntity userEntity) {
        if (userEntity == null) {
            log.error("can't save null user entity");
            throw new IllegalArgumentException("User entity can't be null");
        }
        return userRepository.save(userEntity);
    }

    public Optional<UserEntity> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
