//package com.hungerHub.ecom;
//
//public class AuthControllerTest {
//
//}
package com.hungerHub.ecom;

import com.ecom.controller.AuthController;
import com.ecom.dto.AuthenticationRequest;
import com.ecom.dto.SignupRequest;
import com.ecom.dto.UserDto;
import com.ecom.entity.User;
import com.ecom.enums.UserRole;
import com.ecom.repository.UserRepository;
import com.ecom.services.auth.AuthService;
import com.ecom.services.jwt.UserDetailsServiceImpl;
import com.ecom.utils.JwtUtil;
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
