package com.example.mangoa.application.domain.Repositories;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mangoa.application.domain.model.Accommodation;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, UUID> {
    //Busuqeda de alojamientos activos, por ubicacion , capacidad y precio max
    @Query("""
        SELECT a FROM Accommodation a
        WHERE a.active = true
            AND LOWER(a.location)LIKE LOWER(CONCAT('%',:location, '%'))
            AND a.capacity >= :capacity
            AND a.price <= :maxPrice
            
    """)
    Page<Accommodation> findAvailableAccomodations(
        @Param("location")String location,
        @Param("capacity")Integer capacity,
        @Param("maxPrice")BigDecimal maxPrice,
        Pageable pageable
    );
}
