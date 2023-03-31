package com.order.ordermanagement.object;

import com.order.ordermanagement.dao.entity.OrderItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderItem {
    private UUID orderId;
    private UUID itemId;
    private int count;

    public OrderItemEntity toEntity() {
        return OrderItemEntity.builder()
                .orderId(orderId)
                .itemId(itemId)
                .count(count)
                .build();
    }
}
