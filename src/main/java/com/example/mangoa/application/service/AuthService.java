package com.example.mangoa.application.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mangoa.application.domain.Repositories.UserRepository;
import com.example.mangoa.application.domain.model.User;
import com.example.mangoa.application.dto.AuthResponse;
import com.example.mangoa.application.dto.LoginRequest;
import com.example.mangoa.application.dto.RegisterRequest;
import com.example.mangoa.application.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request){
        if(userRepository.findByEmail(request.email().trim().toLowerCase()).isPresent()){
            throw new IllegalArgumentException("El email ya se encuentra registrado");
        }

        // Sen encripta la contraseña
        String encodedPassword = passwordEncoder.encode(request.password());

        // Se usa el constructor estatico de user
        User user = User.create(
            request.email(),
            encodedPassword,
            User.Role.CUSTOMER, // Rol por defecto en el registro
            request.firstName(),
            request.lastName()
        );

        User savedUSer = userRepository.save(user);
        String token = jwtService.generateToken(savedUSer);

        return AuthResponse.of(token, savedUSer.getEmail(), savedUSer.getRole().name());
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email().trim().toLowerCase(),
                request.password()
            )
        );

        User user = userRepository.findByEmail(request.email().trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Email o contraseña inválidos"));

        String token = jwtService.generateToken(user);

        return AuthResponse.of(token,user.getEmail(), user.getRole().name());
    }
}
