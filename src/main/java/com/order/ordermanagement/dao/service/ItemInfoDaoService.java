package com.order.ordermanagement.dao.service;

import com.order.ordermanagement.dao.entity.ItemInfoEntity;
import com.order.ordermanagement.dao.repository.ItemInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemInfoDaoService {
    private final ItemInfoRepository itemInfoRepository;

    public ItemInfoEntity save(ItemInfoEntity entity) {
        if (entity == null) {
            log.error("Can't save null entity");
            throw new IllegalArgumentException("ItemInfoEntity is null");
        }
        return itemInfoRepository.save(entity);
    }

    public Optional<ItemInfoEntity> findById(UUID id) {
        if (id == null) {
            log.error("Can't find itemInfo by null id");
            throw new IllegalArgumentException("Id is null");
        }
        return itemInfoRepository.findById(id);
    }

    public List<ItemInfoEntity> findByIds(List<UUID> ids){
        return itemInfoRepository.findAllById(ids);
    }

    public void delete(UUID id) {
        if (id == null) {
            log.error("Can't delete itemInfo by null id");
            throw new IllegalArgumentException("Id is null");
        }
        itemInfoRepository.deleteById(id);
    }
}
