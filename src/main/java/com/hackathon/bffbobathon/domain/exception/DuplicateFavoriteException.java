package com.hackathon.bffbobathon.domain.exception;

/**
 * Exceção lançada quando se tenta criar um favorito duplicado.
 */
public class DuplicateFavoriteException extends RuntimeException {

    public DuplicateFavoriteException(Long userId, Long contentId) {
        super(String.format("Usuário %d já favoritou o conteúdo %d", userId, contentId));
    }
}

// Made with Bob