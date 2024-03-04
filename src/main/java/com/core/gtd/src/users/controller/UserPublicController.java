package com.core.gtd.src.users.controller;

import com.core.gtd.src.common.response.BaseResponse;
import com.core.gtd.src.common.security.jwt.JwtToken;
import com.core.gtd.src.users.model.dto.UserDto;
import com.core.gtd.src.users.model.request.UserJoinRequest;
import com.core.gtd.src.users.model.request.UserLoginRequest;
import com.core.gtd.src.users.model.response.UserJoinResponse;
import com.core.gtd.src.users.model.response.UserLoginResponse;
import com.core.gtd.src.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.core.gtd.src.common.response.BaseResponse.*;

@Slf4j
@RestController
@RequestMapping("/public-api")
@RequiredArgsConstructor
public class UserPublicController {

    private final UserService userService;

    @PostMapping("/join")
    public BaseResponse<UserJoinResponse> join(@RequestBody UserJoinRequest request) {

        log.info("Post Mapping - create a new User Request Details: username: {}, email: {}",
                request.getUsername(), request.getEmail());

        UserDto userDto = userService.join(request);

        return response(UserJoinResponse.fromDto(userDto));
    }


    @PostMapping("/login")
    public BaseResponse<UserLoginResponse> login(@RequestBody UserLoginRequest request) {

        log.info("[Post Mapping - User is attempting to login with username: {}", request.getUsername());

        JwtToken jwtToken = userService.login(request);

        return response(UserLoginResponse.toClient(jwtToken));
    }
}
