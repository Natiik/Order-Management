package com.order.ordermanagement.dao.entity;

import com.order.ordermanagement.dao.entity.ids.OrderItemId;
import com.order.ordermanagement.object.OrderItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "order_items")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@IdClass(OrderItemId.class)
public class OrderItemEntity {

    @Id
    @Column(name = "order_id")
    private UUID orderId;

    @Id
    @Column(name = "item_id")
    private UUID itemId;

    @Column(name = "count")
    private int count;

    public OrderItem toData() {
        return new OrderItem(orderId, itemId, count);
    }
}
