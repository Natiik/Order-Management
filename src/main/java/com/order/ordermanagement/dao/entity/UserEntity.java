package com.order.ordermanagement.dao.entity;


import com.order.ordermanagement.object.User;
import com.order.ordermanagement.object.types.Authority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "user_accounts", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "email"}))
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Slf4j
public class UserEntity {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "uuidGenerator", strategy = "com.order.ordermanagement.dao.generator.UuidGenerator")
    @GeneratedValue(generator = "uuidGenerator")
    private UUID id;

    @Column(name = "email")
    private String email;


    @Column(name = "authority")
    private String authority;

    public User toData(){
        return User.builder()
                .id(this.id)
                .email(this.email)
                .authority(Authority.valueOf(authority))
                .build();
    }
}
