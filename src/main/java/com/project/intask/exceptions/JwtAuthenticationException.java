package com.example.test.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
