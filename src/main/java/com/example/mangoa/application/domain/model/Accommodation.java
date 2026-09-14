package com.example.mangoa.application.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import java.util.UUID;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accommodations", indexes = {
    @Index(name = "idx_accommodation_location", columnList = "location"),
    @Index(name = "idx_accommodation_active_price", columnList = "price, active")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "El nombre del alojamiento no puede estar vacío")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "La descripción del alojamiento no puede estar vacía")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "La ubicación del alojamiento no puede estar vacía")
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull(message = "El precio no puede estar vacío")
    @Positive(message = "El precio debe ser un valor positivo")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "La capacidad no puede estar vacía")
    @Positive(message = "La capacidad debe ser un valor positivo")
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @org.springframework.data.annotation.CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private java.time.Instant createdAt;

    @org.springframework.data.annotation.LastModifiedDate
    @Column(name = "updated_at")
    private java.time.Instant updatedAt;

    @Version
    private Long version;

    public static Accommodation create(String name, String description, String location, BigDecimal price, Integer capacity) {
        Accommodation accommodation = new Accommodation();
        accommodation.name = Objects.requireNonNull(name, "El nombre del alojamiento no puede ser nulo").trim();
        accommodation.description = Objects.requireNonNull(description, "La descripción del alojamiento no puede ser nula").trim();
        accommodation.location = Objects.requireNonNull(location, "La ubicación del alojamiento no puede ser nula").trim();
        accommodation.price = Objects.requireNonNull(price, "El precio del alojamiento no puede ser nulo");
        accommodation.capacity = Objects.requireNonNull(capacity, "La capacidad del alojamiento no puede ser nula");
        return accommodation;
    }

        @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        if (this.createdAt == null) {
            this.createdAt = now;
        }
        if (this.updatedAt == null) {
            this.updatedAt = now;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public void deactivate(){ this.active = false; }
    public void activate(){ this.active = true; }

    public void updateName(String newName) {
        if(newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del alojamiento no puede estar vacío");
        }
        this.name = newName.trim();
    }

    public void updateDescription(String newDescription) {
        if(newDescription == null || newDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del alojamiento no puede estar vacía");
        }
        this.description = newDescription.trim();
    }

    public void updateLocation(String newLocation) {
        if(newLocation == null || newLocation.trim().isEmpty()) {
            throw new IllegalArgumentException("La ubicación del alojamiento no puede estar vacía");
        }
        this.location = newLocation.trim();
    }

    public void updatePrice(BigDecimal newPrice) {
        if(newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio del alojamiento no puede ser nulo");
        }
        this.price = newPrice;
    }

    public boolean isAvailableForCapacity(int requestedCapacity) {
        return this.capacity >= requestedCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Accommodation accommodation)) return false;
        return id != null && id.equals(accommodation.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode(); 
    }

}
