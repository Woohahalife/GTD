package com.core.gtd.src.common.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {

    private String accessToken;
    private String grantType;
    private Long expiresIn;

//    public static JwtToken generateToken(String username) {
//        return JwtToken.
//    }
}
