package com.ecomproj.firstecom.service;

import com.ecomproj.firstecom.dto.RegisterRequest;
import com.ecomproj.firstecom.model.User;
import com.ecomproj.firstecom.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo,
                       PasswordEncoder passwordEncoder) {

        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequest request) {

        if (userRepo.findByUsername(request.getUsername()).isPresent()) {

            return "Username already exists";
        }

        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole("ROLE_USER");

        userRepo.save(user);

        return "User Registered Successfully";
    }
}