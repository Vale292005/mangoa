package com.example.mangoa.application.domain.model;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_user_name", columnList = "email", unique = true)
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Column(name = "password_hash", nullable = false)
    private String passwordhash;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @NotNull(message = "El nombre no puede estar vacío")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull(message = "El apellido no puede estar vacío")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "update_at")
    private Instant updatedAt;

    @Version //Control de concurrencia optimista, para evitar que dos transacciones actualicen el mismo registro al mismo tiempo
    private Long version;

    public static User create(String email, String passwordhash, Role role, String firstName, String lastName) {
        User user = new User();
        user.email = Objects.requireNonNull(email, "El email no puede ser nulo").trim().toLowerCase(); 
        user.passwordhash = Objects.requireNonNull(passwordhash, "La contraseña no puede ser nula");
        user.role = role != null ? role : Role.CUSTOMER; // Si el rol es nulo, se asigna el rol por defecto CUSTOMER
        user.firstName = Objects.requireNonNull(firstName, "El nombre no puede ser nulo");
        user.lastName = Objects.requireNonNull(lastName, "El apellido no puede ser nulo");
        return user;
    }

    public void changeEmail(String newEmail) {
        if(newEmail == null || newEmail.trim().isEmpty()){
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        this.email = newEmail.trim().toLowerCase();
    }

    public void updatePassword(String newPasswordHash){
        if(newPasswordHash == null || newPasswordHash.trim().isEmpty()){
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        this.passwordhash = newPasswordHash;
    }

    public boolean isAdmin(){
        return Role.ADMIN.equals(this.role);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof User user)) return false;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
    }

    public enum Role {
        CUSTOMER,
        ADMIN
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));    
    }

    @Override
    public @Nullable String getPassword() {
        return passwordhash;
    }

    @Override
    public String getUsername() {
        return email;
    }
    
}