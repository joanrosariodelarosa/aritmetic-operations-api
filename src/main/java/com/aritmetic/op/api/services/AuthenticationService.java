package com.aritmetic.op.api.services;

import com.aritmetic.op.api.dtos.LoginRequestDto;
import com.aritmetic.op.api.dtos.LoginResponseDto;
import com.aritmetic.op.api.filters.JwtService;
import com.aritmetic.op.api.models.Operation;
import com.aritmetic.op.api.models.Token;
import com.aritmetic.op.api.models.User;
import com.aritmetic.op.api.repositories.OperationRepository;
import com.aritmetic.op.api.repositories.TokenRepository;
import com.aritmetic.op.api.repositories.UserRepository;
import com.aritmetic.op.api.types.OperationType;
import com.aritmetic.op.api.types.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final OperationRepository operationRepository;

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
    // This method is only for table population purpose and test
    public void register(LoginRequestDto request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(jwtToken);
    }


    // This method is only for table population purpose and test
    public void registerOperation() {
        long id = 1;
        OperationType otp = OperationType.Subtraction;
        Operation op = Operation.builder().operationType(otp).id(id).cost(10).build();
        operationRepository.save(op);
    }
}