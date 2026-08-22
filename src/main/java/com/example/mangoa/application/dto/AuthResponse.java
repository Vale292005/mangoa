package com.example.mangoa.application.dto;

public record AuthResponse(
    String token,
    String type,
    String email,
    String role
) {
    public static AuthResponse of(String token, String email, String role){
        return new AuthResponse(token, "Bearer", email, role);
    }
}
