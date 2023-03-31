package com.order.ordermanagement.dao.repository;

import com.order.ordermanagement.dao.entity.ItemInfoEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemInfoRepository extends JpaRepositoryImplementation<ItemInfoEntity, UUID> {
}
