package com.order.ordermanagement.dao.service;

import com.order.ordermanagement.dao.entity.UserCredentialsEntity;
import com.order.ordermanagement.dao.repository.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserCredentialsDaoService {
    private final UserCredentialsRepository userCredentialsRepository;

    public UserCredentialsEntity save(UserCredentialsEntity userCredentials) {
        return userCredentialsRepository.save(userCredentials);
    }

    public Optional<UserCredentialsEntity> findById(UUID userCredentialsId) {
        return userCredentialsRepository.findById(userCredentialsId);
    }

    public void delete(UUID userCredentialsId){
        userCredentialsRepository.deleteById(userCredentialsId);
    }
}
