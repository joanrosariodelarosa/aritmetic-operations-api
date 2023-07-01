package com.aritmetic.op.api.controllers;

import com.aritmetic.op.api.dtos.LoginRequestDto;
import com.aritmetic.op.api.dtos.LoginResponseDto;
import com.aritmetic.op.api.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operation/v1")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/auth")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginRequestDto authRequest) {
        return ResponseEntity.ok(service.authenticate(authRequest));
    }
}

