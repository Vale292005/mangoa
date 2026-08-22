package com.example.mangoa.application.domain.Repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mangoa.application.domain.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking,UUID> {

    List<Booking> findByUserId(UUID userId);

    Page<Booking> findByUserId(UUID userId, Pageable pageable);
    
    @Query("""
            SELECT COUNT(b) > 0
            FROM booking b
            WHERE b.accommodation.id = :accommodationId
            AND b.status IN (PENDING, CONFIRMED)
            AND :checkInDate < b.checkOutDate
            AND :checkOutDate > b.checkInDate
            """)
    boolean hasOverlappingBookings(
        @Param("accommodationId") UUID accommodationId,
        @Param("checkInDate") LocalDate checkInDate,
        @Param("checkOutDate") LocalDate checkOutDate
    );
    
}
