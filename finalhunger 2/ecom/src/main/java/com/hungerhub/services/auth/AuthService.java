package com.hungerhub.services.auth;

import com.hungerhub.dto.SignupRequest;
import com.hungerhub.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);
}
