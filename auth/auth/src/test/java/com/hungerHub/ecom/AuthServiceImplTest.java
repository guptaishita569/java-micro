//package com.hungerHub.ecom;
//
//public class AuthServiceImplTest {
//
//}
package com.hungerHub.ecom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ecom.dto.SignupRequest;
import com.ecom.dto.UserDto;
//import com.ecom.entity.Order;
import com.ecom.entity.User;
//import com.ecom.enums.OrderStatus;
import com.ecom.enums.UserRole;
//import com.ecom.repository.OrderRepository;
import com.ecom.repository.UserRepository;
import com.ecom.services.auth.AuthServiceImpl;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthServiceImplTest {
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private UserRepository userRepository;
//    @Mock
//    private OrderRepository orderRepository;
    @InjectMocks
    private AuthServiceImpl authService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testcreateUser(){
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@test.com");
        signupRequest.setName("Test User");
        signupRequest.setPassword("password");
        User user = new User();
        user.setId(1L);
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword("encodedPassword");
        user.setRole(UserRole.CUSTOMER);
        when(bCryptPasswordEncoder.encode(signupRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
//        Order order = new Order();
//        order.setUser(user);
//        order.setOrderStatus(OrderStatus.Pending);
//        when(orderRepository.save(any(Order.class))).thenReturn(order);
        UserDto result = authService.createUser(signupRequest);
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void testhasUserWithEmail(){
        String email = "test@test.com";
        when(userRepository.findFirstByEmail(email)).thenReturn(Optional.of(new User()));
        boolean result = authService.hasUserWithEmail(email);
        assertTrue(result);
    }

    @Test
    public void testcreateAdminAccount(){
        User adminUser = null;
        when(userRepository.findByRole(UserRole.ADMIN)).thenReturn(adminUser);
        when(bCryptPasswordEncoder.encode("admin")).thenReturn("encodedPassword");
        authService.createAdminAccount();
    }
    @Test
    public void testcreateAdminAccountAdminExists(){
        User adminUser = new User();
        when(userRepository.findByRole(UserRole.ADMIN)).thenReturn(adminUser);
        authService.createAdminAccount();
    }

}
