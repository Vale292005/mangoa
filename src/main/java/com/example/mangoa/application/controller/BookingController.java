package com.example.mangoa.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mangoa.application.dto.BookingResponse;
import com.example.mangoa.application.dto.CreateBookingRequest;
import com.example.mangoa.application.service.BookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    
    private final BookingService bookingService;

    // POST /api/v1/bookings -> Crear reserva
    @PostMapping 
    public ResponseEntity<BookingResponse> creareBooking(
        @Valid @RequestBody CreateBookingRequest request
    ){
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    //GET /api/v1/bookings/{id} -> Consultar por id
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable UUID id){
        BookingResponse response = bookingService.getBookingById(id);
        return ResponseEntity.ok(response);
    }

    //GET /api/v1/bookings/user/{id} -> Busqueda de reservas por usuario
    @GetMapping("/user/{id}")
    public ResponseEntity<Page<BookingResponse>> getBookingByUserId(
        @PathVariable UUID userID,
        @PageableDefault(size = 10, sort = "createdAt") Pageable pageable){
            Page<BookingResponse> response = bookingService.getBookingsByUserId(userID, pageable);
            return ResponseEntity.ok(response);
    }

    //PATCH /api/v1/bookings/{id}/confirm -> Confirmar reserva
    @PatchMapping("/{id}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(@PathVariable UUID id){
        BookingResponse response = bookingService.confirmBooking(id);
        return ResponseEntity.ok(response);
    }

    //PATCH /api/v1/bookings/{id}/cancel -> Cancelar reserva
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable UUID id){
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    //GET /api/v1/bookings -> listar todos los bookings
    @GetMapping()
    public ResponseEntity<Page<BookingResponse>> getBookings(
        @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        Page<BookingResponse> response = bookingService.getBookings(pageable);
        return ResponseEntity.ok(response);
    }

}
