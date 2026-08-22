package com.example.mangoa.application.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookings", indexes = {
    @Index(name = "idx_booking_user", columnList = "user_id"),
    @Index(name = "idx_booking_accommodation", columnList = "accommodation_id, check_in_date, check_out_date")
})
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)

public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    @NotNull(message = "La fecha de check-in no puede estar vacía")
    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @NotNull(message = "La fecha de check-out no puede estar vacía")
    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @NotNull(message = "El precio total no puede estar vacío")
    @Positive(message = "El precio total debe ser un valor positivo")
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private BookingStatus status;

    @org.springframework.data.annotation.CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @org.springframework.data.annotation.LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Version
    private Long version;



    public static Booking create(User user, Accommodation accommodation, LocalDate checkInDate, LocalDate checkOutDate) {
        Objects.requireNonNull(user, "El usuario no puede ser nulo");
        Objects.requireNonNull(accommodation, "El alojamiento no puede ser nulo");
        Objects.requireNonNull(checkInDate, "La fecha de check-in no puede ser nula");
        Objects.requireNonNull(checkOutDate, "La fecha de check-out no puede ser nula");
        if (!accommodation.getActive()) {
            throw new IllegalArgumentException("El alojamiento no está activo");
        }
        if (checkInDate.isAfter(checkOutDate)) {
            throw new IllegalArgumentException("La fecha de check-in no puede ser posterior a la fecha de check-out");
        }
        if (checkInDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de check-in no puede ser anterior a la fecha actual");
        }
        long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        BigDecimal totalPrice1 = accommodation.getPrice().multiply(BigDecimal.valueOf(days));
        
        Booking booking = new Booking();
        booking.user = user;
        booking.accommodation = accommodation;
        booking.checkInDate = checkInDate;
        booking.checkOutDate = checkOutDate;
        booking.totalPrice = totalPrice1;
        booking.status = BookingStatus.PENDING;
        return booking;
    }

    //operations to change the status of the booking

    public void confirm() {
        if(this.status != BookingStatus.PENDING) {
            throw new IllegalStateException("Solo se pueden confirmar reservas pendientes");
        }
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        if(this.status != BookingStatus.PENDING) {
            throw new IllegalStateException("Solo se pueden cancelar reservas pendientes");
        }
        this.status = BookingStatus.CANCELLED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        CANCELLED
    }
}


