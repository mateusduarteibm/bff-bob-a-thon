package com.hackathon.bffbobathon.domain.exception;

/**
 * Exceção lançada quando um favorito não é encontrado.
 */
public class FavoriteNotFoundException extends RuntimeException {

    public FavoriteNotFoundException(Long id) {
        super(String.format("Favorito com ID %d não encontrado", id));
    }

    public FavoriteNotFoundException(Long userId, Long contentId) {
        super(String.format("Favorito não encontrado para usuário %d e conteúdo %d", userId, contentId));
    }
}

// Made with Bob