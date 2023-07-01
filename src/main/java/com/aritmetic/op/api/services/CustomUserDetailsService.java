package com.aritmetic.op.api.services;

import com.aritmetic.op.api.models.User;
import com.aritmetic.op.api.repositories.UserRepository;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Data
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;
    private User user;


    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserData(username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public User getUserData(String userName) {
        User user = repository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        setUser(user);
        return user;
    }

}
