package com.order.ordermanagement.service;

import com.order.ordermanagement.controller.model.request.ItemCount;
import com.order.ordermanagement.object.Order;
import com.order.ordermanagement.object.User;
import com.order.ordermanagement.object.types.Status;

import javax.management.OperationsException;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order create(User user, List<ItemCount> items, String comment);

    Order editOrder(User user, UUID orderId, List<ItemCount> items, String comment) throws OperationsException;

    void changeOrderStatus(User user, UUID orderId, Status newStatus) throws OperationsException;

    Order findById(User user, UUID orderId) throws OperationsException;

    List<Order> findAll(User user);

    void delete(User user, UUID orderId) throws OperationsException;

}
