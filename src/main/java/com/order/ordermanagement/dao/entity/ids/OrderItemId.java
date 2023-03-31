package com.order.ordermanagement.dao.entity.ids;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderItemId implements Serializable {
    private UUID orderId;
    private UUID itemId;
}
