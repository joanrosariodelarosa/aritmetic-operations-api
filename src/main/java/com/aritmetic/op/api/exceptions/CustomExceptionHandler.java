package com.aritmetic.op.api.exceptions;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.aritmetic.op.api.Constants.UNKNOWN_ERROR;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<OperationResponseDto> handleCalculatorException(CustomException ex) {
        return handleBadResponseEntity(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<OperationResponseDto> handleBadCredentialsException(BadCredentialsException ex) {
        return handleBadResponseEntity(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<OperationResponseDto> handleGenericException(Exception e) {
        return handleBadResponseEntity(UNKNOWN_ERROR);
    }

    private ResponseEntity<OperationResponseDto> handleBadResponseEntity(String errorMessage) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                OperationResponseDto.builder()
                        .errorMessage(errorMessage)
                        .build());
    }
}