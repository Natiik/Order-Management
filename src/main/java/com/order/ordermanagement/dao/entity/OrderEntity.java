package com.order.ordermanagement.dao.entity;

import com.order.ordermanagement.object.types.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "orders", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OrderEntity {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "uuidGenerator", strategy = "com.order.ordermanagement.dao.generator.UuidGenerator")
    @GeneratedValue(generator = "uuidGenerator")
    private UUID id;

    @Column(name = "number")
    private String number;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "created_time")
    private long createdTime;

    @Column(name = "payment_time")
    private long paymentTime;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "sum_value")
    private float sum;

}
