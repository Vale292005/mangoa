package com.example.mangoa.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe proporcionar un email válido")
    String email,

    @NotBlank(message = "La contraseña no puede estar vácia")
    String password
) {
}
