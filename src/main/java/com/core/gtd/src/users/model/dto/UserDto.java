package com.core.gtd.src.users.model.dto;

import com.core.gtd.src.common.constatnt.Role;
import com.core.gtd.src.users.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String address;
    private LocalDateTime createdAt;

    private Role role;

    public static UserDto fromEntity(Users users) {
        return UserDto.builder()
                .id(users.getId())
                .username(users.getUsername())
                .password(users.getPassword())
                .name(users.getName())
                .email(users.getEmail())
                .address(users.getAddress())
                .createdAt(users.getCreatedAt())
                .role(users.getRole())
                .build();
    }
}
