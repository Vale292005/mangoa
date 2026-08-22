package com.example.mangoa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AbstractUserDetailsReactiveAuthenticationManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.mangoa.application.domain.Repositories.UserRepository;
import com.example.mangoa.application.domain.model.User;
import com.example.mangoa.application.dto.AuthResponse;
import com.example.mangoa.application.dto.LoginRequest;
import com.example.mangoa.application.dto.RegisterRequest;
import com.example.mangoa.application.security.jwt.JwtService;
import com.example.mangoa.application.service.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;
    
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;
    
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.create(
            "valeria.test@gmail.com",
            "encondePassword123",
            User.Role.CUSTOMER,
            "Florez",
            "Valeria"
        );
    }

    @Test
    @DisplayName("Debe registrar un nuevo usuario exitosamente y retornar jwt")
    void shouldRegisterNewUserSeccesfully() {
        RegisterRequest request = new RegisterRequest(
            "valeria.test@gmail.com",
            "Password123!",
            "Valeria",
            "Florez"
        );

        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword123");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(jwtService.generateToken(any(User.class))).thenReturn("mocked.jwt.token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("mocked.jwt.token", response.token());
        assertEquals("valeria.test@gmail.com", response.email());
        assertEquals("CUSTOMER", response.role());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Debe authenticar correctamente en login y devolver el Jwt")
    void shouldLoginSuccesfully(){
        LoginRequest request = new LoginRequest(
            "valeria.test@gmail.com",
            "Password123!"
        );

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken(testUser)).thenReturn("mocked.jwt.token");
        
        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("mocked.jwt.token", response.token());
        assertEquals("valeria.test@gmail.com", response.email());

        verify(authenticationManager, times(1)).authenticate(any());
    }
    
}
