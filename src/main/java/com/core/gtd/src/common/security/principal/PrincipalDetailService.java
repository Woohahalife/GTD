package com.core.gtd.src.common.security.principal;

import com.core.gtd.src.common.exception.AppException;
import com.core.gtd.src.common.security.principal.UserPrincipal;
import com.core.gtd.src.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.core.gtd.src.common.response.ResponseStatus.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserPrincipal::new)
                .orElseThrow(() -> new AppException(USER_NOT_FOUND));
    }
}
