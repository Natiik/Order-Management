package com.order.ordermanagement.service;

import com.order.ordermanagement.controller.model.request.ItemCount;
import com.order.ordermanagement.dao.entity.OrderEntity;
import com.order.ordermanagement.dao.entity.OrderItemEntity;
import com.order.ordermanagement.dao.service.OrderDaoService;
import com.order.ordermanagement.dao.service.OrderItemDaoService;
import com.order.ordermanagement.object.Item;
import com.order.ordermanagement.object.Order;
import com.order.ordermanagement.object.OrderItem;
import com.order.ordermanagement.object.User;
import com.order.ordermanagement.object.types.Authority;
import com.order.ordermanagement.object.types.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.OperationsException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseOrderService implements OrderService {
    private final ItemService itemService;
    private final OrderDaoService orderDaoService;
    private final OrderItemDaoService orderItemDaoService;

    @Override
    public Order create(User user, List<ItemCount> items, String comment) {
        float sum = calculateOrderSum(items);

        Order order = new Order(user.getId(), Collections.emptyList(), comment, sum);
        OrderEntity orderEntity = order.toEntity();
        OrderEntity saved = orderDaoService.save(orderEntity);

        List<OrderItemEntity> orderItems = items.stream()
                .map(item -> new OrderItem(saved.getId(), item.itemId(), item.count()))
                .map(item -> orderItemDaoService.save(item.toEntity()))
                .toList();

        orderItems.forEach(en -> itemService.changeItemCount(en.getItemId(), en.getCount()));
        return new Order(saved, orderItems);
    }

    @Override
    public Order editOrder(User user, UUID orderId, List<ItemCount> items, String comment) throws OperationsException {
        Order oldOrder = findById(user, orderId);

        addNewItemsToOrder(oldOrder, items);
        deleteUselessItemsFromOrder(oldOrder, items);

        float sum = calculateOrderSum(items);
        List<OrderItem> orderItems = items.stream()
                .map(i -> new OrderItem(orderId, i.itemId(), i.count()))
                .toList();

        Order order = new Order(oldOrder, orderItems, sum, comment);
        orderDaoService.save(order.toEntity());
        return order;
    }


    @Override
    public void changeOrderStatus(User user, UUID orderId, Status newStatus) throws OperationsException {
        Order order = findById(user, orderId);
        switch (newStatus) {
            case CREATED -> throw new OperationsException("Can't change orderId to created");
            case PROCESSING -> order.markAsProcessing();
            case SHIPPING -> order.markAsShipping();
            case DELIVERED -> order.markAsDelivered();
        }
        orderDaoService.save(order.toEntity());
    }

    @Override
    public Order findById(User user, UUID orderId) throws OperationsException {
        Optional<OrderEntity> optionalOrder = orderDaoService.findById(orderId);
        if (optionalOrder.isEmpty()) {
            log.error("Failed to find order with id {}", orderId);
            throw new NoSuchElementException("No order found");
        }
        OrderEntity order = optionalOrder.get();
        if (Authority.USER.equals(user.getAuthority()) && !order.getUserId().equals(user.getId())) {
            log.error("User {} can't read order {} created by {}", user.getId(), order.getId(), order.getUserId());
            throw new OperationsException("Forbidden");
        }
        List<OrderItemEntity> orderItems = orderItemDaoService.findAllByOrderId(orderId);
        return new Order(order, orderItems);
    }

    @Override
    public List<Order> findAll(User user) {
        if (Objects.requireNonNull(user.getAuthority()) == Authority.USER) {
            return orderDaoService.findAllByUserId(user.getId())
                    .stream()
                    .map(order -> {
                        List<OrderItemEntity> items = orderItemDaoService.findAllByOrderId(order.getId());
                        return new Order(order, items);
                    })
                    .toList();
        }
        return orderDaoService.findAll()
                .stream()
                .map(order -> {
                    List<OrderItemEntity> items = orderItemDaoService.findAllByOrderId(order.getId());
                    return new Order(order, items);
                })
                .toList();
    }

    @Override
    public void delete(User user, UUID orderId) throws OperationsException {
        Order order = findById(user, orderId);
        orderDaoService.delete(order.getId());
    }

    private float calculateOrderSum(List<ItemCount> orderItems) {
        List<UUID> itemIds = orderItems.stream()
                .map(ItemCount::itemId)
                .toList();
        Map<UUID, Float> itemsPrice = itemService.findByIds(itemIds).stream()
                .collect(Collectors.toMap(Item::getId, Item::getPrice));
        //todo as improvement validate that item count in db > 0
        return (float) orderItems.stream()
                .mapToDouble(item -> item.count() * itemsPrice.get(item.itemId()))
                .sum();
    }

    private void deleteUselessItemsFromOrder(Order order, List<ItemCount> items) {
        Map<UUID, Integer> newItems = items.stream()
                .collect(Collectors.toMap(
                        ItemCount::itemId,
                        ItemCount::count
                ));

        order.getItems().stream()
                .map(i -> {
                    Integer newCount = newItems.get(i.getItemId());
                    if (newCount == null || newCount < i.getCount()) {
                        int increase = newCount == null ? i.getCount() : newCount - i.getCount();
                        itemService.changeItemCount(i.getItemId(), increase);
                        if (newCount == null) {
                            orderItemDaoService.delete(order.getId(), i.getItemId());
                            return null;
                        } else {
                            i.setCount(newCount);
                            return i;
                        }

                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .map(OrderItem::toEntity)
                .forEach(orderItemDaoService::save);
    }

    private void addNewItemsToOrder(Order order, List<ItemCount> items) {
        Map<UUID, Integer> oldItems = order.getItems()
                .stream()
                .collect(Collectors.toMap(
                        OrderItem::getItemId,
                        OrderItem::getCount
                ));
        items.stream()
                .filter(i -> {
                    Integer oldCount = oldItems.get(i.itemId());
                    if (oldCount == null || oldCount < i.count()) {
                        int decrease = oldCount == null ? i.count() : i.count() - oldCount;
                        itemService.changeItemCount(i.itemId(), decrease);
                        return true;
                    }
                    return false;
                })
                .map(i -> new OrderItem(order.getId(), i.itemId(), i.count()))
                .map(OrderItem::toEntity)
                .forEach(orderItemDaoService::save);

    }

}
