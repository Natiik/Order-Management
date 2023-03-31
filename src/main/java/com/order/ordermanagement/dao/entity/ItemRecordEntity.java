package com.order.ordermanagement.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Entity
@Table(name = "items_records", uniqueConstraints = @UniqueConstraint(columnNames = {"item_id"}))
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Slf4j
public class ItemRecordEntity {
    @Id
    @Column(name = "item_id")
    private UUID itemId;

    @Column(name = "count")
    private int count;
}
