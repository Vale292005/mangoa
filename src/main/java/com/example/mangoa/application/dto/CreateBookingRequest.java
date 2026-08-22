package com.example.mangoa.application.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record CreateBookingRequest(

    @NotNull(message = "El ID del usuario es obligatorio")
    UUID userId,

    @NotNull(message = "El ID del alojamiento es obligatorio")
    UUID accommodationId,

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio debe ser hoy o en el futuro")
    LocalDate checkInDate,

    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser en el futuro")
    LocalDate checkOutDate
) {
}
