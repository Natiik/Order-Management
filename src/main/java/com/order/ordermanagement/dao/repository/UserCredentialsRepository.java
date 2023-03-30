package com.order.ordermanagement.dao.repository;

import com.order.ordermanagement.dao.entity.UserCredentialsEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserCredentialsRepository extends JpaRepositoryImplementation<UserCredentialsEntity, UUID> {
}
