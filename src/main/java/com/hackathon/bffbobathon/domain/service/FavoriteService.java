package com.hackathon.bffbobathon.domain.service;

import com.hackathon.bffbobathon.adapter.input.dto.CreateFavoriteRequest;
import com.hackathon.bffbobathon.adapter.output.memory.ContentRepository;
import com.hackathon.bffbobathon.adapter.output.memory.FavoriteRepository;
import com.hackathon.bffbobathon.domain.entity.Content;
import com.hackathon.bffbobathon.domain.entity.Favorite;
import com.hackathon.bffbobathon.domain.exception.ContentNotFoundException;
import com.hackathon.bffbobathon.domain.exception.DuplicateFavoriteException;
import com.hackathon.bffbobathon.domain.exception.FavoriteNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço de domínio para gerenciamento de favoritos.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ContentRepository contentRepository;

    /**
     * Adiciona um conteúdo aos favoritos do usuário.
     */
    public Favorite addFavorite(CreateFavoriteRequest request) {
        log.info("Adicionando favorito - userId: {}, contentId: {}", 
                request.getUserId(), request.getContentId());

        // Valida se o conteúdo existe
        if (!contentRepository.existsById(request.getContentId())) {
            log.warn("Tentativa de favoritar conteúdo inexistente: {}", request.getContentId());
            throw new ContentNotFoundException(request.getContentId());
        }

        // Verifica se já existe favorito
        if (favoriteRepository.existsByUserIdAndContentId(request.getUserId(), request.getContentId())) {
            log.warn("Usuário {} já favoritou o conteúdo {}", 
                    request.getUserId(), request.getContentId());
            throw new DuplicateFavoriteException(request.getUserId(), request.getContentId());
        }

        Favorite favorite = Favorite.builder()
                .userId(request.getUserId())
                .contentId(request.getContentId())
                .build();

        Favorite savedFavorite = favoriteRepository.save(favorite);
        log.info("Favorito criado com sucesso. ID: {}", savedFavorite.getId());

        return savedFavorite;
    }

    /**
     * Remove um favorito por ID.
     */
    public void removeFavorite(Long id) {
        log.info("Removendo favorito ID: {}", id);

        if (!favoriteRepository.existsById(id)) {
            throw new FavoriteNotFoundException(id);
        }

        favoriteRepository.deleteById(id);
        log.info("Favorito removido com sucesso. ID: {}", id);
    }

    /**
     * Remove um favorito específico de um usuário.
     */
    public void removeFavoriteByUserAndContent(Long userId, Long contentId) {
        log.info("Removendo favorito - userId: {}, contentId: {}", userId, contentId);

        Favorite favorite = favoriteRepository.findByUserIdAndContentId(userId, contentId)
                .orElseThrow(() -> new FavoriteNotFoundException(userId, contentId));

        favoriteRepository.deleteById(favorite.getId());
        log.info("Favorito removido com sucesso. ID: {}", favorite.getId());
    }

    /**
     * Lista todos os favoritos de um usuário.
     */
    public List<Favorite> getUserFavorites(Long userId) {
        log.debug("Listando favoritos do usuário: {}", userId);
        return favoriteRepository.findByUserId(userId);
    }

    /**
     * Lista todos os favoritos de um usuário com informações dos conteúdos.
     */
    public Map<Favorite, Content> getUserFavoritesWithContent(Long userId) {
        log.debug("Listando favoritos com conteúdos do usuário: {}", userId);
        
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        
        return favorites.stream()
                .collect(Collectors.toMap(
                        favorite -> favorite,
                        favorite -> contentRepository.findById(favorite.getContentId()).orElse(null)
                ));
    }

    /**
     * Busca um favorito por ID.
     */
    public Favorite getFavoriteById(Long id) {
        log.debug("Buscando favorito por ID: {}", id);
        return favoriteRepository.findById(id)
                .orElseThrow(() -> new FavoriteNotFoundException(id));
    }

    /**
     * Verifica se um usuário favoritou um conteúdo específico.
     */
    public boolean isFavorited(Long userId, Long contentId) {
        log.debug("Verificando se usuário {} favoritou conteúdo {}", userId, contentId);
        return favoriteRepository.existsByUserIdAndContentId(userId, contentId);
    }

    /**
     * Conta quantos favoritos um usuário tem.
     */
    public long countUserFavorites(Long userId) {
        log.debug("Contando favoritos do usuário: {}", userId);
        return favoriteRepository.countByUserId(userId);
    }

    /**
     * Lista todos os favoritos (admin).
     */
    public List<Favorite> getAllFavorites() {
        log.debug("Listando todos os favoritos");
        return favoriteRepository.findAll();
    }
}

// Made with Bob