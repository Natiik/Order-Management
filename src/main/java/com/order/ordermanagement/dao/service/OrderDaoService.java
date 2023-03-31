package com.order.ordermanagement.dao.service;

import com.order.ordermanagement.dao.entity.OrderEntity;
import com.order.ordermanagement.dao.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDaoService {
    private final OrderRepository repository;

    public OrderEntity save(OrderEntity entity) {
        if (entity == null) {
            log.error("Can't save null entity");
            throw new IllegalArgumentException("OrderEntity is null");
        }
        return repository.save(entity);
    }

    public Optional<OrderEntity> findById(UUID id) {
        if (id == null) {
            log.error("Can't find order by null id");
            throw new IllegalArgumentException("Id is null");
        }
        return repository.findById(id);
    }

    public List<OrderEntity> findAllByUserId(UUID userId) {
        if (userId == null) {
            log.error("Can't find order by null user  userId");
            throw new IllegalArgumentException("UserId is null");
        }
        return repository.findAllByUserId(userId);
    }

    public List<OrderEntity> findAll() {
        return repository.findAll();
    }

    public void delete(UUID id) {
        if (id == null) {
            log.error("Can't delete order by null user  id");
            throw new IllegalArgumentException("UserId is null");
        }
        repository.deleteById(id);
    }
}
