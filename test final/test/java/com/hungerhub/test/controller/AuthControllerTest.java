package com.hungerhub.test.controller;

import com.hungerhub.controller.AuthController;
import com.hungerhub.dto.AuthenticationRequest;
import com.hungerhub.dto.SignupRequest;
import com.hungerhub.dto.UserDto;
import com.hungerhub.entity.User;
import com.hungerhub.enums.UserRole;
import com.hungerhub.repository.UserRepository;
import com.hungerhub.services.auth.AuthService;
import com.hungerhub.services.jwt.UserDetailsServiceImpl;
import com.hungerhub.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthService authService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AuthController authController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testcreateAuthenticationToken()throws IOException, JSONException {
        String username = "testuser";
        String password = "password";
        String jwt = "sampleJwtToken";
        long userId = 1L;
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        User user = new User();
        user.setId(userId);
        user.setRole(UserRole.CUSTOMER);
        user.setEmail("testuser");
        user.setName("testuser");
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername(username);
        authenticationRequest.setPassword(password);
        when(userRepository.findFirstByEmail(username)).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(username)).thenReturn(jwt);
        authController.createAuthenticationToken(authenticationRequest,response);
    }

    @Test
    public void testCreateAuthenticationToken_InvalidCredentials() throws IOException {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        String username = "testuser";
        String password = "password";
        authenticationRequest.setUsername(username);
        authenticationRequest.setPassword(password);
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);
        assertThrows(BadCredentialsException.class, () -> authController.createAuthenticationToken(authenticationRequest, response));
    }
    @Test
    public void testSignupUser_UserExists() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("testuser");
        signupRequest.setEmail("test@test.com");
        signupRequest.setPassword("password");
        when(authService.hasUserWithEmail(anyString())).thenReturn(true);
        ResponseEntity<?> responseEntity = authController.signupUser(signupRequest);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
        assertEquals("User already exists", responseEntity.getBody());
    }
    @Test
    public void testSignupUser_UserDoesNotExist() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("testuser");
        signupRequest.setEmail("test@test.com");
        signupRequest.setPassword("password");
        when(authService.hasUserWithEmail(anyString())).thenReturn(false);
        UserDto userDto = new UserDto();
        when(authService.createUser(any(SignupRequest.class))).thenReturn(userDto);
        ResponseEntity<?> responseEntity = authController.signupUser(signupRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userDto, responseEntity.getBody());
    }
}
