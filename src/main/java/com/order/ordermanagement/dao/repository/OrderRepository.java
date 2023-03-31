package com.order.ordermanagement.dao.repository;

import com.order.ordermanagement.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepositoryImplementation<OrderEntity, UUID> {
    List<OrderEntity> findAllByUserId(UUID userId);
}
