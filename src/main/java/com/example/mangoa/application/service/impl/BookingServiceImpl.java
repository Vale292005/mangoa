package com.example.mangoa.application.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mangoa.application.domain.Repositories.AccommodationRepository;
import com.example.mangoa.application.domain.Repositories.BookingRepository;
import com.example.mangoa.application.domain.Repositories.UserRepository;
import com.example.mangoa.application.domain.exception.ResourceNotFoundException;
import com.example.mangoa.application.domain.model.Accommodation;
import com.example.mangoa.application.domain.model.Booking;
import com.example.mangoa.application.domain.model.User;
import com.example.mangoa.application.dto.BookingResponse;
import com.example.mangoa.application.dto.CreateBookingRequest;
import com.example.mangoa.application.service.BookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    @Override
    @Transactional
    public BookingResponse createBooking(CreateBookingRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el ID: " + request.userId()));
        Accommodation accommodation = accommodationRepository.findById(request.accommodationId())
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado con el ID: " + request.accommodationId()));
                
        boolean isOcupied = bookingRepository.hasOverlappingBookings(
            request.accommodationId(),
            request.checkInDate(),
            request.checkOutDate()
        );

        if(isOcupied){
            throw new IllegalArgumentException("El alojamiento ya está reservado para las fechas seleccionadas");
        }

        Booking booking = Booking.create(
            user,
            accommodation,
            request.checkInDate(),
            request.checkOutDate()
        );
        
        Booking savedBooking = bookingRepository.save(booking);
        return BookingResponse.fromEntity(savedBooking);
     }

    @Override
    public BookingResponse getBookingById(UUID id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con el ID: " + id));
        return BookingResponse.fromEntity(booking);
    }

    @Override
    public Page<BookingResponse> getBookingsByUserId(UUID userId, Pageable pageable) {
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("Usuario no encontrado con el ID: " + userId);
        }
        return bookingRepository.findByUserId(userId, pageable)
                .map(BookingResponse::fromEntity);
    }

    @Override
    @Transactional
    public BookingResponse confirmBooking(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con el ID: " + bookingId));
        booking.confirm();
        return BookingResponse.fromEntity(booking);
    }

    @Override
    @Transactional
    public void cancelBooking(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con el ID: " + bookingId));
        booking.cancel();
     }
}
