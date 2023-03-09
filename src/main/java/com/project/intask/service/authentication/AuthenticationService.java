package com.project.intask.service.board.authentication;

import com.example.test.dto.authentication.AuthenticationRequest;
import com.example.test.dto.authentication.AuthenticationResponse;
import com.example.test.dto.authentication.RegisterRequest;
import com.example.test.exceptions.UserAlreadyExistException;
import com.example.test.exceptions.UserNotFoundException;
import com.example.test.model.User;
import com.example.test.repository.UserRepository;
import com.example.test.security.jwt.JwtTokenProvider;
import com.example.test.service.role.RoleService;
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

    public AuthenticationResponse register(RegisterRequest request)
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
