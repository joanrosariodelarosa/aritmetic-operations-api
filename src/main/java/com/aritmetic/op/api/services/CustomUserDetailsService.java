package com.aritmetic.op.api.services;

import com.aritmetic.op.api.models.User;
import com.aritmetic.op.api.repositories.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Data
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private User user;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public User getSecuredUser() {
        if (user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                user = (User) authentication.getPrincipal();
            }
        }
        return user;
    }
}
