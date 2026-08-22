package com.example.mangoa.application.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAccommodationRequest(
    @NotBlank(message = "El nombre del alojamiento no puede estar vacío")
    @Size(max = 100, message = "El nombre del alojamiento no puede tener más de 100 caracteres")
    String name,

    @Size(max = 255, message = "La descripción del alojamiento no puede tener más de 255 caracteres")
    String description,

    @NotBlank(message = "La ubicación del alojamiento es obligatoria")
    String location,

    @NotNull(message = "El precio por noche es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio por noche debe ser mayor que cero")
    BigDecimal price,

    @NotNull(message = "La capacidad del alojamiento es obligatoria")
    @DecimalMin(value = "1", message = "La capacidad del alojamiento debe ser al menos 1")
    Integer capacity
) {
    
}
