package com.order.ordermanagement.dao.entity;

import com.order.ordermanagement.object.UserCredentials;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Entity
@Table(name = "user_credentials", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Slf4j
public class UserCredentialsEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "password")
    private String password;

    public UserCredentials toData() {
        return UserCredentials.builder()
                .userId(this.id)
                .password(this.password)
                .build();
    }
}
