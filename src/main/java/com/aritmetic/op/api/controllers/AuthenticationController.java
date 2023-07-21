package com.aritmetic.op.api.controllers;

import com.aritmetic.op.api.dtos.LoginRequestDto;
import com.aritmetic.op.api.dtos.LoginResponseDto;
import com.aritmetic.op.api.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/operation")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/v1/auth")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginRequestDto authRequest) {
        return ResponseEntity.ok(service.authenticate(authRequest));
    }
}

