package com.example.mangoa.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mangoa.application.dto.AuthResponse;
import com.example.mangoa.application.dto.LoginRequest;
import com.example.mangoa.application.dto.RegisterRequest;
import com.example.mangoa.application.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuhtController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.register(request);
        ResponseCookie cookie = ResponseCookie.from("access_token", authResponse.token())
                .httpOnly(true)
                .secure(false) // Cambiar a true en producción si se usa HTTPS
                .path("/")
                .maxAge(24 * 60 * 60) // 1 día en segundos
                .sameSite("lax") // Cambiar según la política de SameSite que desees
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        AuthResponse responseWithCookie = new AuthResponse(
            null,
            "Bearer",
            authResponse.email(),
            authResponse.role()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseWithCookie);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> Login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.login(request);
        ResponseCookie cookie = ResponseCookie.from("access_token", authResponse.token())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        AuthResponse responseWithCookie = new AuthResponse(
            null,
            "Bearer",
            authResponse.email(),
            authResponse.role()
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseWithCookie);
    }

}
