package com.order.ordermanagement.dao.repository;

import com.order.ordermanagement.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
}
