package com.project.intask.service.authentication;

import com.project.intask.dto.authentication.AuthenticationRequest;
import com.project.intask.dto.authentication.AuthenticationResponse;
import com.project.intask.exceptions.UserAlreadyExistException;
import com.project.intask.exceptions.UserNotFoundException;
import com.project.intask.model.User;
import com.project.intask.repository.UserRepository;
import com.project.intask.jwt.JwtTokenProvider;
import com.project.intask.service.role.RoleService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(AuthenticationRequest request)
            throws UserAlreadyExistException {

        if (userRepository.getUserByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("Username already exists");
        }
        User newUser = User
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(roleService.getByName("USER")))
                .build();

        userRepository.save(newUser);

        String token = jwtTokenProvider.createToken(newUser.getUsername(), newUser.getRoles());
        return AuthenticationResponse
            .builder()
            .token(token)
            .build();

    }

    public AuthenticationResponse authentication(AuthenticationRequest request)
            throws UserNotFoundException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()
                ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Authentication failed");
        }

        User user = userRepository.getUserByUsername(request.getUsername()).get();
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());

        return AuthenticationResponse.builder()
            .token(token)
            .build();
    }
}
