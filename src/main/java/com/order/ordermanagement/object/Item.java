package com.order.ordermanagement.object;

import com.order.ordermanagement.dao.entity.ItemInfoEntity;
import com.order.ordermanagement.dao.entity.ItemRecordEntity;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    private UUID id;
    private String name;
    private float price;
    private int count;

    public ItemInfoEntity toInfoEntity() {
        return ItemInfoEntity.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .build();
    }

    public ItemRecordEntity toRecordEntity() {
        return ItemRecordEntity.builder()
                .itemId(this.id)
                .count(this.count)
                .build();
    }
}
