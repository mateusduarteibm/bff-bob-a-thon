package com.hackathon.bffbobathon.adapter.input.exception;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Classe para padronização de respostas de erro de validação da API.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationErrorResponse {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private Map<String, String> errors;
}

// Made with Bob
