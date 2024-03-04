package com.core.gtd.src.common.security;

import com.core.gtd.src.common.exception.AppException;
import com.core.gtd.src.common.security.principal.UserPrincipal;
import com.core.gtd.src.users.model.dto.UserDto;
import com.core.gtd.src.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

import static com.core.gtd.src.common.response.ResponseStatus.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUserResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자가 인증되지 않은 경우 예외 처리
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            throw new AppException(NOT_AUTHENTICATED_USER);
        }

//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserPrincipal userPrincipal = userRepository.findByUsername(userDetails.getUsername())
                .map(UserPrincipal::new)
                .orElseThrow(() -> new AppException(USER_NOT_FOUND));

        return UserDto.fromEntity(userPrincipal.getUsers().orElseThrow(() -> new AppException(PRINCIPAL_IS_NOTFOUND)));
    }
}
