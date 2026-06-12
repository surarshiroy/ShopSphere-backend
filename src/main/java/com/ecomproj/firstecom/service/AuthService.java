package com.ecomproj.firstecom.service;
import com.ecomproj.firstecom.dto.AuthResponse;
import com.ecomproj.firstecom.security.JwtService;
import com.ecomproj.firstecom.dto.AuthRequest;
import com.ecomproj.firstecom.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request) {

        var user = userRepo
                .findByUsername(request.getUsername())
                .orElseThrow();

        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!matches) {
            throw new RuntimeException(
                    "Invalid Credentials"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getUsername()
                );

        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRole()
        );
    }
}