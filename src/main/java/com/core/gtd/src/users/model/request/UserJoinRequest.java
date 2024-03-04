package com.core.gtd.src.users.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {

    private String username;
    private String password;
    private String name;
    private String email;
    private String address;
}
