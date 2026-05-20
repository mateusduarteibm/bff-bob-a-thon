package com.hackathon.bffbobathon.domain.exception;

/**
 * Exceção lançada quando um conteúdo não é encontrado.
 */
public class ContentNotFoundException extends RuntimeException {

    public ContentNotFoundException(Long id) {
        super(String.format("Conteúdo com ID %d não encontrado", id));
    }

    public ContentNotFoundException(String message) {
        super(message);
    }
}

// Made with Bob
