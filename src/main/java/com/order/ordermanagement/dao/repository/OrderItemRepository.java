package com.order.ordermanagement.dao.repository;

import com.order.ordermanagement.dao.entity.OrderItemEntity;
import com.order.ordermanagement.dao.entity.ids.OrderItemId;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepositoryImplementation<OrderItemEntity, OrderItemId> {
    List<OrderItemEntity> findAllByOrderId(UUID orderId);
}
