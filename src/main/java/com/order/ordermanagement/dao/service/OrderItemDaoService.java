package com.order.ordermanagement.dao.service;

import com.order.ordermanagement.dao.entity.OrderItemEntity;
import com.order.ordermanagement.dao.entity.ids.OrderItemId;
import com.order.ordermanagement.dao.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemDaoService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemEntity save(OrderItemEntity entity) {
        if (entity == null) {
            log.error("Can't save null entity");
            throw new IllegalArgumentException("OrderItemEntity is null");
        }
        return orderItemRepository.save(entity);
    }

    public List<OrderItemEntity> findAllByOrderId(UUID orderId) {
        if (orderId == null) {
            log.error("Can't find order item by null order id");
            throw new IllegalArgumentException("Id is null");
        }
        return orderItemRepository.findAllByOrderId(orderId);
    }


    public void delete(UUID orderId, UUID itemId) {
        if (orderId == null || itemId == null) {
            log.error("Can't delete order item by null id");
            throw new IllegalArgumentException("Id is null");
        }
        orderItemRepository.deleteById(new OrderItemId(orderId, itemId));
    }

}
