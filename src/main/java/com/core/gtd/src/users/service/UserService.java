package com.core.gtd.src.users.service;

import com.core.gtd.src.common.constatnt.Role;
import com.core.gtd.src.common.exception.AppException;
import com.core.gtd.src.common.security.jwt.JwtToken;
import com.core.gtd.src.common.security.jwt.JwtTokenGenerator;
import com.core.gtd.src.users.entity.Users;
import com.core.gtd.src.users.model.dto.UserDto;
import com.core.gtd.src.users.model.request.UserJoinRequest;
import com.core.gtd.src.users.model.request.UserLoginRequest;
import com.core.gtd.src.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import static com.core.gtd.src.common.response.ResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Transactional
    public UserDto join(UserJoinRequest request) {
        /*
        회원 정보(username, password, name, email, address)를 등록한다.
            -  username이 이미 존재할 경우 에러 반환
            -  중복 이메일 허용 x
         */

        duplicateUsername(request.getUsername());
        duplicateUserEmail(request.getEmail());

        Users users = insertUserDataFromRequest(request);

        Users save = userRepository.save(users);

        log.info("UserEntity has created for join with ID: {} username:{} email: {} address : {}",
                users.getId(), users.getUsername(), users.getEmail(), users.getAddress());

        return UserDto.fromEntity(save);
    }

    private Users insertUserDataFromRequest(UserJoinRequest request) {
        return Users.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .email(request.getEmail())
                .role(Role.USER)
                .address(request.getAddress())
                .build();
    }


    @Transactional
    public JwtToken login(UserLoginRequest request) {
        /*
        로그인 기능
            - username 등록되어 있지 않다면 에러 반환
            - username 이 password 와 일치하지 않는다면 에러 반환
         */

        Users users = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(USER_NOT_FOUND));

        UserDto userDto = UserDto.fromEntity(users);

        validatePassword(request, userDto);

        return jwtTokenGenerator.generateAccessToken(userDto.getUsername(), userDto.getRole());
    }

    private void validatePassword(UserLoginRequest request, UserDto userDto) {

        if(!passwordEncoder.matches(request.getPassword(), userDto.getPassword())) {
            throw new AppException(INVALID_PASSWORD);
        }
    }

    private void duplicateUserEmail(String email) {
        userRepository.findByEmail(email)
                .ifPresent(users -> {
                    throw new AppException(DUPLICATE_EMAIL);
                });
    }

    private void duplicateUsername(String username) {
        userRepository.findByUsername(username)
                .ifPresent(users -> {
                    throw new AppException(DUPLICATE_USERNAME);
                } );
    }


}
