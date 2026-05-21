package com.hackathon.bffbobathon.adapter.output.memory;

import com.hackathon.bffbobathon.domain.entity.Favorite;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Repositório em memória para armazenamento de favoritos.
 * Usa ConcurrentHashMap para thread-safety.
 */
@Repository
public class FavoriteRepository {

    private final Map<Long, Favorite> favorites = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Salva um novo favorito.
     */
    public Favorite save(Favorite favorite) {
        if (favorite.getId() == null) {
            favorite.setId(idGenerator.getAndIncrement());
        }
        if (favorite.getCreatedAt() == null) {
            favorite.setCreatedAt(LocalDateTime.now());
        }
        favorites.put(favorite.getId(), favorite);
        return favorite;
    }

    /**
     * Busca um favorito por ID.
     */
    public Optional<Favorite> findById(Long id) {
        return Optional.ofNullable(favorites.get(id));
    }

    /**
     * Lista todos os favoritos.
     */
    public List<Favorite> findAll() {
        return new ArrayList<>(favorites.values());
    }

    /**
     * Busca favoritos por userId.
     */
    public List<Favorite> findByUserId(Long userId) {
        return favorites.values().stream()
                .filter(fav -> fav.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    /**
     * Busca um favorito específico por userId e contentId.
     */
    public Optional<Favorite> findByUserIdAndContentId(Long userId, Long contentId) {
        return favorites.values().stream()
                .filter(fav -> fav.getUserId().equals(userId) && fav.getContentId().equals(contentId))
                .findFirst();
    }

    /**
     * Verifica se existe um favorito para userId e contentId.
     */
    public boolean existsByUserIdAndContentId(Long userId, Long contentId) {
        return favorites.values().stream()
                .anyMatch(fav -> fav.getUserId().equals(userId) && fav.getContentId().equals(contentId));
    }

    /**
     * Deleta um favorito por ID.
     */
    public void deleteById(Long id) {
        favorites.remove(id);
    }

    /**
     * Verifica se um favorito existe.
     */
    public boolean existsById(Long id) {
        return favorites.containsKey(id);
    }

    /**
     * Conta quantos favoritos um usuário tem.
     */
    public long countByUserId(Long userId) {
        return favorites.values().stream()
                .filter(fav -> fav.getUserId().equals(userId))
                .count();
    }
}

// Made with Bob