package com.example.mangoa.application.service.impl;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mangoa.application.domain.Repositories.AccommodationRepository;
import com.example.mangoa.application.domain.exception.ResourceNotFoundException;
import com.example.mangoa.application.domain.model.Accommodation;
import com.example.mangoa.application.dto.AccommodationResponse;
import com.example.mangoa.application.dto.CreateAccommodationRequest;
import com.example.mangoa.application.service.AccomodationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccommodationServiceImpl implements AccomodationService{

    private final AccommodationRepository accommodationRepository;

    @Override
    @Transactional
    public AccommodationResponse createAccomodation(CreateAccommodationRequest request) {
        Accommodation accommodation = Accommodation.create(
            request.name(),
            request.description(),
            request.location(),
            request.price(),
            request.capacity()
        );

        Accommodation savedAccommodation = accommodationRepository.save(accommodation);
        return AccommodationResponse.fromEntity(savedAccommodation);
    }

    @Override
    public AccommodationResponse getAccomodationById(UUID id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado con el ID: " + id));
                return AccommodationResponse.fromEntity(accommodation);
    }

    @Override
    public Page<AccommodationResponse> searchAviableAccommodations(
        String location, 
        Integer capacity, 
        BigDecimal maxPrice,
        Pageable pageable) 
        {
                String searchLocation = (location == null) ? "" : location.trim();
                Integer searchCapacity = (capacity == null) ? 1 : capacity;
                BigDecimal searchMaxPrice = (maxPrice == null) ? new BigDecimal("999999.99") : maxPrice;

                return accommodationRepository.findAvaibleAccomodations(
                    searchLocation, searchCapacity, searchMaxPrice, pageable)
                    .map(AccommodationResponse::fromEntity);
             }

    @Override
    @Transactional
    public void deactivateAccommodation(UUID id) {
        Accommodation accmmodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado con el ID: " + id));
        accmmodation.deactivate();
     }
    
}
