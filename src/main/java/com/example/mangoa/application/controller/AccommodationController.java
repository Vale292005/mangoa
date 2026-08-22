package com.example.mangoa.application.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mangoa.application.dto.AccommodationResponse;
import com.example.mangoa.application.dto.CreateAccommodationRequest;
import com.example.mangoa.application.service.AccomodationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/accommodations")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccomodationService accomodationService;

    // POST /api/v1/accommodations -> Crear un nuevo alojamiento
    @PostMapping
    public ResponseEntity<AccommodationResponse> createAccommodation(
        @Valid @RequestBody CreateAccommodationRequest request
    ){
        AccommodationResponse response = accomodationService.createAccomodation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/v1/accommodations/{id} -> Consultar por id
    @GetMapping("/{id}")
    public ResponseEntity<AccommodationResponse> getAccommodationById(@PathVariable UUID id){
        AccommodationResponse response = accomodationService.getAccomodationById(id);
        return ResponseEntity.ok(response);
    }

    // GET /api/v1/accommodations -> Búsqueda de filtros páginados
    @GetMapping
    public ResponseEntity<Page<AccommodationResponse>> searchAccommodations(
        @RequestParam(required = false) String location,
        @RequestParam(required = false) Integer capacity,
        @RequestParam(required = false) BigDecimal maxPrice,
        @PageableDefault(size = 10, sort = "createdAt") Pageable pageable 
    ){
        Page<AccommodationResponse> response = accomodationService.searchAviableAccommodations(location, capacity, maxPrice, pageable);
        return ResponseEntity.ok(response);
    }

    // PATCH /api/v1/accommodation/{id}/deactivate -> Desactivar
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateAccommodation(@PathVariable UUID id){
        accomodationService.deactivateAccommodation(id);
        return ResponseEntity.noContent().build();
    }
    
}
