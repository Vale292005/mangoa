package com.example.mangoa.application.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.example.mangoa.application.domain.model.Accommodation;

public record AccommodationResponse(
    UUID id,
    String name,
    String description,
    String location,
    Double price,
    Integer capacity,
    Boolean active,
    Instant createdAt
){
    public static AccommodationResponse fromEntity(Accommodation accommodation) {
        return new AccommodationResponse(
            accommodation.getId(),
            accommodation.getName(),
            accommodation.getDescription(),
            accommodation.getLocation(),
            accommodation.getPrice().doubleValue(),
            accommodation.getCapacity(),
            accommodation.getActive(),
            accommodation.getCreatedAt()
        );
    }
}
