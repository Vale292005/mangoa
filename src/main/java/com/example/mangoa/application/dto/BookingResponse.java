package com.example.mangoa.application.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.example.mangoa.application.domain.model.Booking.BookingStatus;

public record BookingResponse(
    UUID id,
    UUID userId,
    String userName,
    UUID accommodationId,
    String accommodationName,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    BigDecimal totalPrice,
    BookingStatus status,
    Instant createdAt
) {
    public static BookingResponse fromEntity(com.example.mangoa.application.domain.model.Booking booking) {
        return new BookingResponse(
            booking.getId(),
            booking.getUser().getId(),
            booking.getUser().getFirstName() + " " + booking.getUser().getLastName(),
            booking.getAccommodation().getId(),
            booking.getAccommodation().getName(),
            booking.getCheckInDate(),
            booking.getCheckOutDate(),
            booking.getTotalPrice(),
            booking.getStatus(),
            booking.getCreatedAt()
        );
    }
}
