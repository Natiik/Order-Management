package com.order.ordermanagement.object;

import com.order.ordermanagement.dao.entity.UserEntity;
import com.order.ordermanagement.object.types.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class User {
    private UUID id;
    private String email;
    private String password;
    private Authority authority;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .authority(this.authority.name())
                .build();
    }
}
