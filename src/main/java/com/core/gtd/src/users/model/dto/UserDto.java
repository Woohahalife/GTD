package com.core.gtd.src.users.model.dto;

import com.core.gtd.common.constatnt.Role;
import com.core.gtd.src.users.entity.Users;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String userName;
    private String password;
    private String name;
    private String email;
    private String address;

    private Role role;

    public static UserDto UserDto(Users users) {
        return UserDto.builder()
                .id(users.getId())
                .userName(users.getUserName())
                .password(users.getPassword())
                .name(users.getName())
                .email(users.getEmail())
                .address(users.getAddress())
                .build();
    }
}
