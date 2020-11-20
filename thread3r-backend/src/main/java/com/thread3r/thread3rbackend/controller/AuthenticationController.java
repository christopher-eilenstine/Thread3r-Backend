package com.thread3r.thread3rbackend.controller;

import com.thread3r.thread3rbackend.dto.auth.LoginRequest;
import com.thread3r.thread3rbackend.dto.auth.RegisterRequest;
import com.thread3r.thread3rbackend.dto.Response;
import com.thread3r.thread3rbackend.dto.auth.TokenResponse;
import com.thread3r.thread3rbackend.model.UserEntity;
import com.thread3r.thread3rbackend.repository.Thread3rUserRepository;
import com.thread3r.thread3rbackend.security.AuthenticationUtils;
import com.thread3r.thread3rbackend.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationUtils authenticationUtils;
    private final Thread3rUserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationUtils authenticationUtils,
                                    Thread3rUserRepository userRepository, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.authenticationUtils = authenticationUtils;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = authenticationUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new TokenResponse(
                userDetails.getId(), userDetails.getEmail(), userDetails.getUsername(), token, "Bearer"
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new Response("Username is already taken!"));
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new Response("Email is already taken!"));
        }

        UserEntity user = UserEntity.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(encoder.encode(registerRequest.getPassword()))
                .build();

        userRepository.save(user);
        return ResponseEntity.ok(new Response("User registered successfully!"));
    }

}
