package com.project.intask.controller.authentication;

import com.project.intask.dto.authentication.AuthenticationRequest;
import com.project.intask.dto.authentication.AuthenticationResponse;
import com.project.intask.dto.registration.RegistrationRequest;
import com.project.intask.dto.registration.RegistrationResponse;
import com.project.intask.exceptions.UserAlreadyExistException;
import com.project.intask.exceptions.UserNotFoundException;
import com.project.intask.service.authentication.AuthenticationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public RegistrationResponse register(@RequestBody @Valid RegistrationRequest request)
            throws UserAlreadyExistException {
        return authenticationService.register(request);
    }

    @PostMapping("/auth")
    public AuthenticationResponse authentication(
            @RequestBody @Valid AuthenticationRequest request)
            throws UserNotFoundException, AuthenticationException {

        return authenticationService.authentication(request);
    }
}
