package com.order.ordermanagement.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "items_info", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "name"}))
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Slf4j
public class ItemInfoEntity {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "uuidGenerator", strategy = "com.order.ordermanagement.dao.generator.UuidGenerator")
    @GeneratedValue(generator = "uuidGenerator")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private float price;

}
