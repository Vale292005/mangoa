package com.example.mangoa.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank
    @Email(message = "Debe proporcionar un email válido")
    String email,

    @NotBlank(message = "El email no puede estar vaćio")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    String password,

    @NotBlank(message = "El nombre no puede estar vacío")
    String firstName,

    @NotBlank(message = "El apellido no puede estar vacío")
    String lastName
    
) {
    
}
