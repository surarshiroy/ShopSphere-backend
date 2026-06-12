package com.ecomproj.firstecom.controller;
import com.ecomproj.firstecom.dto.AuthResponse;
import org.springframework.security.core.Authentication;
import com.ecomproj.firstecom.dto.AuthRequest;
import com.ecomproj.firstecom.dto.RegisterRequest;
import com.ecomproj.firstecom.service.AuthService;
import com.ecomproj.firstecom.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService,
                          AuthService authService) {

        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request) {

        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody AuthRequest request) {

        return authService.login(request);
    }
    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {

        Map<String, Object> response = new HashMap<>();

        response.put("username", authentication.getName());

        response.put(
                "role",
                authentication.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()
        );

        return response;
    }
}