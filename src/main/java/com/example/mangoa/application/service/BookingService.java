package com.example.mangoa.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.mangoa.application.dto.BookingResponse;
import com.example.mangoa.application.dto.CreateBookingRequest;

public interface BookingService {
    
    BookingResponse createBooking(CreateBookingRequest request);

    BookingResponse getBookingById(UUID id);

    Page<BookingResponse> getBookingsByUserId(UUID userId, Pageable pageable);

    BookingResponse confirmBooking(UUID bookingId);

    void cancelBooking(UUID bookingId);
}
