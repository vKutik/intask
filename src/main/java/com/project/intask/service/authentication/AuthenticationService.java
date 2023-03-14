package com.project.intask.service.authentication;

import com.project.intask.dto.authentication.AuthenticationRequest;
import com.project.intask.dto.authentication.AuthenticationResponse;
import com.project.intask.dto.registration.RegistrationRequest;
import com.project.intask.dto.registration.RegistrationResponse;
import com.project.intask.exceptions.UserAlreadyExistException;
import com.project.intask.exceptions.UserNotFoundException;

public interface AuthenticationService {

    RegistrationResponse register(RegistrationRequest request) throws UserAlreadyExistException;

    AuthenticationResponse authentication(AuthenticationRequest request)
                throws UserNotFoundException;

}
