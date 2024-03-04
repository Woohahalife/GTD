package com.core.gtd.src.common.config;

import com.core.gtd.src.common.security.AuthUserResolver;
import com.core.gtd.src.users.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserRepository userRepository;

    public WebMvcConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthUserResolver(userRepository));
    }
}
