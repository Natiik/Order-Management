package com.order.ordermanagement.dao.service;

import com.order.ordermanagement.dao.entity.ItemRecordEntity;
import com.order.ordermanagement.dao.repository.ItemRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRecordDaoService {
    private final ItemRecordRepository itemRecordRepository;

    public ItemRecordEntity save(ItemRecordEntity entity) {
        if (entity == null) {
            log.error("Can't save null entity");
            throw new IllegalArgumentException("ItemRecordEntity is null");
        }
        return itemRecordRepository.save(entity);
    }

    public Optional<ItemRecordEntity> findById(UUID id) {
        if (id == null) {
            log.error("Can't find itemRecord by null id");
            throw new IllegalArgumentException("Id is null");
        }
        return itemRecordRepository.findById(id);
    }

    public List<ItemRecordEntity> findByIds(List<UUID> ids){
        return itemRecordRepository.findAllById(ids);
    }

    public void delete(UUID id) {
        if (id == null) {
            log.error("Can't delete itemRecord by null id");
            throw new IllegalArgumentException("Id is null");
        }
        itemRecordRepository.deleteById(id);
    }
}
