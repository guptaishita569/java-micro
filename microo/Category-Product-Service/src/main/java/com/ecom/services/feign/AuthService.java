package com.ecom.services.feign;

import com.ecom.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "auth-service", url = "http://localhost:8081")
public interface AuthService {

    @GetMapping("/auth/user/{id}")
    ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id);

}
