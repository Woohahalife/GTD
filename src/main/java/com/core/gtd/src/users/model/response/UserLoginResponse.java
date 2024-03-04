package com.core.gtd.src.users.model.response;

import com.core.gtd.src.common.security.jwt.JwtToken;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {

    private String accessToken;
    private String grantType;
    private Long expiresIn;

    public static UserLoginResponse toClient(JwtToken jwtToken) {
        return UserLoginResponse.builder()
                .accessToken(jwtToken.getAccessToken())
                .grantType(jwtToken.getGrantType())
                .expiresIn(jwtToken.getExpiresIn())
                .build();
    }
}
