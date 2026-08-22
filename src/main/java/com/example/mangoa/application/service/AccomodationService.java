package com.example.mangoa.application.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.mangoa.application.dto.AccommodationResponse;
import com.example.mangoa.application.dto.CreateAccommodationRequest;

public interface AccomodationService {

    AccommodationResponse createAccomodation(CreateAccommodationRequest request);
    
    AccommodationResponse getAccomodationById(UUID id);
    
    Page<AccommodationResponse> searchAviableAccommodations(
        String location,
        Integer capacity,
        BigDecimal maxPrice,
        Pageable pageable
    );

    void deactivateAccommodation(UUID id);
}
