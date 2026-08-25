package com.example.mangoa.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.mangoa.application.security.jwt.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Collections;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private final String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private final long expiration = 3600000;

    @BeforeEach
    void setUp(){
        jwtService = new JwtService();

        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", expiration);
    }

    @Test
    @DisplayName("Debe generar un token JWT válido y extraer el username correctamente")
    void genertateToken_ShouldReturnValidToken(){
        when(userDetails.getUsername()).thenReturn("valeria.test@gmail.com");
        when(userDetails.getAuthorities()).thenReturn((Collection)List.of(new SimpleGrantedAuthority("ROLE_USER")));
        
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        String extracedUsername = jwtService.extractUsername(token);
        assertEquals("valeria.test@gmail.com", extracedUsername);
    }

    @Test
    @DisplayName("Debe validar que un token generado pertenece al usuario correcto")
    void isTokenValid_ShouldReturnTrue_WhenTokenIsValid(){
        when(userDetails.getUsername()).thenReturn("valeria.test@gmail.com");
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    @DisplayName("Debe retornar false al validar el token con un usuario distinto")
    void isTokenValid_ShouldReturnFalse_WhenUsernameDoesNotMatch(){
        when(userDetails.getUsername()).thenReturn("valeria.test@gmail.com");
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        String token = jwtService.generateToken(userDetails);

        UserDetails otherUser = org.mockito.Mockito.mock(UserDetails.class);
        when(otherUser.getUsername()).thenReturn("otro@example.com");

        boolean isValid = jwtService.isTokenValid(token, otherUser);

        assertFalse(isValid);
    }

@Test
    @DisplayName("Debe retornar false cuando el token ha expirado")
    void isTokenValid_ShouldReturnFalse_WhenTokenIsExpired() {
        // Configuramos expiración negativa para simular un token vencido
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", -1000L);

        when(userDetails.getUsername()).thenReturn("valeria@example.com");
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        String expiredToken = jwtService.generateToken(userDetails);

        assertThrows(Exception.class, () -> {
            jwtService.isTokenValid(expiredToken, userDetails);
        });
    }
}
