package com.core.gtd.src.users.model.response;

import com.core.gtd.src.users.model.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinResponse {

    private String username;
    private String password;
    private String name;
    private LocalDateTime createdAt;

    public static UserJoinResponse fromDto(UserDto userDto) {
        return UserJoinResponse.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .createdAt(userDto.getCreatedAt())
                .build();
    }
}
