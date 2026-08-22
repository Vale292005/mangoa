package com.example.mangoa.application.dto;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    int status,
    String error,
    String message,
    String path,
    Instant timestamp,
    Map<String,String> validationErrors
) {
    //Constructor para errores generales
    public static ErrorResponse of(int status, String error, String message, String path) {
        return new ErrorResponse(status, error, message, path, Instant.now(), null);
    }

    //Constructor para errores de validación
    public static ErrorResponse of(int status, String error, String message, String path, Map<String,String> validationErrors){
        return new ErrorResponse(status, error, message, path,Instant.now(), validationErrors);
    }
} 
