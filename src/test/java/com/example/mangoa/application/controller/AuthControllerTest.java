package com.example.mangoa.application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.mangoa.application.dto.AuthResponse;
import com.example.mangoa.application.dto.LoginRequest;
import com.example.mangoa.application.dto.RegisterRequest;
import com.example.mangoa.application.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuhtController authController;

    @BeforeEach
    void setUp() {
        // Inicializa MockMvc solo para este controlador sin levantar contexto Spring
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @DisplayName("Debe registrar un usuario exitosamente y retornar HTTP 201 Created")
    void register_ShouldReturnCreatedAndAuthResponse() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(
            "valeria@example.com",
            "password123",
            "Valeria",
            "Florez"
        );

        AuthResponse authResponse = new AuthResponse("mocked-jwt-token", null, null, null);

        when(authService.register(any(RegisterRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));
    }

    @Test
    @DisplayName("Debe autenticar un usuario correctamente y retornar HTTP 200 OK")
    void login_ShouldReturnOkAndAuthResponse() throws Exception {
        LoginRequest loginRequest = new LoginRequest(
            "valeria@example.com",
            "password123"
        );

        AuthResponse authResponse = new AuthResponse("mocked-jwt-token", null, null, null);

        when(authService.login(any(LoginRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));
    }
}