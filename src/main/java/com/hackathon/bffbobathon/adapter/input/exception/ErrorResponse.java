package com.hackathon.bffbobathon.adapter.input.exception;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Classe para padronização de respostas de erro da API.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
}

// Made with Bob
