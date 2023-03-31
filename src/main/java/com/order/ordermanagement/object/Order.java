package com.order.ordermanagement.object;

import com.order.ordermanagement.dao.entity.OrderEntity;
import com.order.ordermanagement.dao.entity.OrderItemEntity;
import com.order.ordermanagement.object.types.Status;
import com.order.ordermanagement.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.management.OperationsException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Order {
    private UUID id;
    private String number;
    private UUID userId;
    private long createdTime;
    private long paymentTime;
    private Status status;
    private String comment;
    private List<OrderItem> items;
    private float sum;

    public Order(UUID userId, List<OrderItem> items, String comment, float sum) {
        this.userId = userId;
        this.number = RandomUtil.generateNumericStr(8);
        this.createdTime = System.currentTimeMillis();
        this.comment = comment;
        this.status = Status.CREATED;
        this.sum = sum;
        this.items = items;
        this.paymentTime = -1;
    }

    public Order(OrderEntity orderEntity, List<OrderItemEntity> itemEntities) {
        this.id = orderEntity.getId();
        this.number = orderEntity.getNumber();
        this.userId = orderEntity.getUserId();
        this.createdTime = orderEntity.getCreatedTime();
        this.paymentTime = orderEntity.getPaymentTime();
        this.status = Status.valueOf(orderEntity.getStatus());
        this.comment = orderEntity.getComment();
        this.sum = orderEntity.getSum();
        this.items = itemEntities == null ?
                Collections.emptyList() :
                itemEntities.stream().map(OrderItemEntity::toData).collect(Collectors.toList());
    }

    public Order(Order order, List<OrderItem> items, float sum, String comment) {
        this.id = order.getId();
        this.number = order.getNumber();
        this.userId = order.getUserId();
        this.createdTime = order.getCreatedTime();
        this.paymentTime = order.getPaymentTime();
        this.status = order.getStatus();
        this.comment = comment;
        this.sum = sum;
        this.items = items == null ? Collections.emptyList() : items;
    }

    public void markAsProcessing() {
        this.status = Status.PROCESSING;
    }

    public boolean isProcessed() {
        return Status.SHIPPING.equals(this.status) || Status.DELIVERED.equals(this.status);
    }

    public void markAsShipping() throws OperationsException {
        if (this.paymentTime <= 0) {
            throw new OperationsException("Can't ship not payed order");
        }
        this.status = Status.SHIPPING;
    }

    public void markAsPayed() {
        this.paymentTime = System.currentTimeMillis();
    }

    public void markAsDelivered() throws OperationsException {
        if (!Status.SHIPPING.equals(this.status)) {
            throw new OperationsException("Can't deliver not  order that was not shipping");
        }
        this.status = Status.DELIVERED;
    }

    public OrderEntity toEntity() {
        return OrderEntity.builder()
                .id(this.id)
                .number(this.number)
                .comment(this.comment)
                .createdTime(this.createdTime)
                .paymentTime(this.paymentTime)
                .userId(this.userId)
                .status(this.status.name())
                .comment(this.comment)
                .sum(this.sum)
                .build();
    }
}
