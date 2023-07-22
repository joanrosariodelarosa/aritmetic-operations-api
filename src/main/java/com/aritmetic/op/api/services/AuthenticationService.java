package com.aritmetic.op.api.services;

import com.aritmetic.op.api.dtos.LoginRequestDto;
import com.aritmetic.op.api.dtos.LoginResponseDto;
import com.aritmetic.op.api.filters.JwtService;
import com.aritmetic.op.api.models.Token;
import com.aritmetic.op.api.repositories.TokenRepository;
import com.aritmetic.op.api.types.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    private final TokenRepository tokenRepository;

    public LoginResponseDto authenticate(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );
        var user = customUserDetailsService.loadUserByUsername(request.getUsername());
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(jwtToken);
        var refreshToken = jwtService.generateRefreshToken(user);
        return LoginResponseDto.builder()
                .userName(user.getUsername())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(String jwtToken) {
        var token = Token.builder()
                .user(customUserDetailsService.getUser())
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

}