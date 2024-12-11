package com.ecom.services.auth;

import com.ecom.dto.SignupRequest;
import com.ecom.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);

    UserDto getUserById(Long id);
}
