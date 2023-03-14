package com.project.intask.service.authentication;

import com.project.intask.dto.authentication.AuthenticationRequest;
import com.project.intask.dto.authentication.AuthenticationResponse;
import com.project.intask.dto.registration.RegistrationRequest;
import com.project.intask.dto.registration.RegistrationResponse;
import com.project.intask.exceptions.UserAlreadyExistException;
import com.project.intask.exceptions.UserNotFoundException;
import com.project.intask.jwt.JwtTokenProvider;
import com.project.intask.model.User;
import com.project.intask.repository.UserRepository;
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
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public RegistrationResponse register(RegistrationRequest request)
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

        return new RegistrationResponse("User successfully registered");

    }

    public AuthenticationResponse authentication(AuthenticationRequest request)
            throws UserNotFoundException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()
                ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        User user = userRepository.getUserByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Incorrect username or password"));
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());

        return AuthenticationResponse.builder()
            .token(token)
            .build();
    }
}
