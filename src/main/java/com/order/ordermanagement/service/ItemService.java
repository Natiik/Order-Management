package com.order.ordermanagement.service;

import com.order.ordermanagement.object.Item;

import java.util.List;
import java.util.UUID;

public interface ItemService {
    Item save(Item item);

    Item findById(UUID id);

    List<Item> findByIds(List<UUID> ids);
    void delete(UUID id);

    void changeItemCount(UUID itemId, int count);
}
