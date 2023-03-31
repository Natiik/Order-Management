package com.order.ordermanagement.service;

import com.order.ordermanagement.dao.entity.ItemInfoEntity;
import com.order.ordermanagement.dao.entity.ItemRecordEntity;
import com.order.ordermanagement.dao.service.ItemInfoDaoService;
import com.order.ordermanagement.dao.service.ItemRecordDaoService;
import com.order.ordermanagement.object.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseItemService implements ItemService {
    private final ItemInfoDaoService itemInfoDaoService;
    private final ItemRecordDaoService itemRecordDaoService;

    @Override
    public Item save(Item item) {
        if (item.getCount() < 0) {
            log.error("Can't save item with count less than 0 [itemName: {}]", item.getName());
            throw new IllegalArgumentException("Count can't be less than 0");
        }

        ItemInfoEntity entity = item.toInfoEntity();
        ItemInfoEntity saved = itemInfoDaoService.save(entity);
        itemRecordDaoService.save(item.toRecordEntity());
        item.setId(saved.getId());
        return item;
    }

    @Override
    public Item findById(UUID id) {
        Optional<ItemInfoEntity> info = itemInfoDaoService.findById(id);
        if (info.isEmpty()) {
            log.error("Found no item with id {}", id);
            throw new NoSuchElementException("No item found");
        }
        return getItem(info.get());
    }

    @Override
    public List<Item> findByIds(List<UUID> ids) {
        List<ItemInfoEntity> infos = itemInfoDaoService.findByIds(ids);
        if (infos.size() != ids.size()) {
            log.error("Failed to find items all ids");
            throw new NoSuchElementException("No items found");
        }
        return infos.stream()
                .map(this::getItem)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        itemRecordDaoService.delete(id);
        itemInfoDaoService.delete(id);
    }

    @Override
    public void changeItemCount(UUID itemId, int count) {
        Item item = findById(itemId);
        int newCount = item.getCount() - count;
        item.setCount(newCount);
        save(item);
    }

    private Item getItem(ItemInfoEntity info) {
        Optional<ItemRecordEntity> record = itemRecordDaoService.findById(info.getId());
        if (record.isEmpty()) {
            return Item.builder()
                    .id(info.getId())
                    .name(info.getName())
                    .price(info.getPrice())
                    .count(0)
                    .build();
        }
        return Item.builder()
                .id(info.getId())
                .name(info.getName())
                .price(info.getPrice())
                .count(record.get().getCount())
                .build();
    }
}
