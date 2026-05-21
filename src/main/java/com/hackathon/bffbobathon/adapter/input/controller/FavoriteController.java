package com.hackathon.bffbobathon.adapter.input.controller;

import com.hackathon.bffbobathon.adapter.input.dto.CreateFavoriteRequest;
import com.hackathon.bffbobathon.adapter.input.dto.FavoriteDTO;
import com.hackathon.bffbobathon.adapter.input.mapper.FavoriteMapper;
import com.hackathon.bffbobathon.domain.entity.Content;
import com.hackathon.bffbobathon.domain.entity.Favorite;
import com.hackathon.bffbobathon.domain.service.ContentService;
import com.hackathon.bffbobathon.domain.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller REST para gerenciamento de favoritos.
 */
@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Favorites", description = "API para gerenciamento de conteúdos favoritos")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final ContentService contentService;
    private final FavoriteMapper favoriteMapper;

    /**
     * Adiciona um conteúdo aos favoritos.
     */
    @PostMapping
    @Operation(summary = "Adicionar favorito", description = "Adiciona um conteúdo aos favoritos do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Favorito criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Conteúdo não encontrado"),
            @ApiResponse(responseCode = "409", description = "Favorito já existe")
    })
    public ResponseEntity<FavoriteDTO> addFavorite(
            @Valid @RequestBody CreateFavoriteRequest request) {
        log.info("POST /favorites - Adicionando favorito para userId: {}, contentId: {}", 
                request.getUserId(), request.getContentId());

        Favorite favorite = favoriteService.addFavorite(request);
        
        // Busca o conteúdo para retornar informações completas
        Content content = contentService.getContentById(favorite.getContentId());
        FavoriteDTO response = favoriteMapper.toDTOWithContent(favorite, content);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Lista todos os favoritos de um usuário.
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar favoritos do usuário", description = "Retorna todos os favoritos de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<List<FavoriteDTO>> getUserFavorites(
            @Parameter(description = "ID do usuário") @PathVariable Long userId) {
        log.info("GET /favorites/user/{} - Listando favoritos do usuário", userId);

        Map<Favorite, Content> favoritesWithContent = favoriteService.getUserFavoritesWithContent(userId);
        
        List<FavoriteDTO> response = favoritesWithContent.entrySet().stream()
                .map(entry -> favoriteMapper.toDTOWithContent(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * Verifica se um usuário favoritou um conteúdo específico.
     */
    @GetMapping("/user/{userId}/content/{contentId}")
    @Operation(summary = "Verificar se está favoritado", description = "Verifica se um usuário favoritou um conteúdo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    })
    public ResponseEntity<Map<String, Boolean>> checkFavorite(
            @Parameter(description = "ID do usuário") @PathVariable Long userId,
            @Parameter(description = "ID do conteúdo") @PathVariable Long contentId) {
        log.info("GET /favorites/user/{}/content/{} - Verificando favorito", userId, contentId);

        boolean isFavorited = favoriteService.isFavorited(userId, contentId);
        
        return ResponseEntity.ok(Map.of("isFavorited", isFavorited));
    }

    /**
     * Remove um favorito por ID.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover favorito por ID", description = "Remove um favorito específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Favorito removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Favorito não encontrado")
    })
    public ResponseEntity<Void> removeFavorite(
            @Parameter(description = "ID do favorito") @PathVariable Long id) {
        log.info("DELETE /favorites/{} - Removendo favorito", id);

        favoriteService.removeFavorite(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Remove um favorito específico de um usuário.
     */
    @DeleteMapping("/user/{userId}/content/{contentId}")
    @Operation(summary = "Remover favorito específico", description = "Remove um favorito específico de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Favorito removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Favorito não encontrado")
    })
    public ResponseEntity<Void> removeFavoriteByUserAndContent(
            @Parameter(description = "ID do usuário") @PathVariable Long userId,
            @Parameter(description = "ID do conteúdo") @PathVariable Long contentId) {
        log.info("DELETE /favorites/user/{}/content/{} - Removendo favorito", userId, contentId);

        favoriteService.removeFavoriteByUserAndContent(userId, contentId);

        return ResponseEntity.noContent().build();
    }

    /**
     * Conta quantos favoritos um usuário tem.
     */
    @GetMapping("/user/{userId}/count")
    @Operation(summary = "Contar favoritos do usuário", description = "Retorna a quantidade de favoritos de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    })
    public ResponseEntity<Map<String, Long>> countUserFavorites(
            @Parameter(description = "ID do usuário") @PathVariable Long userId) {
        log.info("GET /favorites/user/{}/count - Contando favoritos", userId);

        long count = favoriteService.countUserFavorites(userId);

        return ResponseEntity.ok(Map.of("count", count));
    }

    /**
     * Busca um favorito por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar favorito por ID", description = "Retorna um favorito específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorito encontrado"),
            @ApiResponse(responseCode = "404", description = "Favorito não encontrado")
    })
    public ResponseEntity<FavoriteDTO> getFavoriteById(
            @Parameter(description = "ID do favorito") @PathVariable Long id) {
        log.info("GET /favorites/{} - Buscando favorito", id);

        Favorite favorite = favoriteService.getFavoriteById(id);
        Content content = contentService.getContentById(favorite.getContentId());
        FavoriteDTO response = favoriteMapper.toDTOWithContent(favorite, content);

        return ResponseEntity.ok(response);
    }
}

// Made with Bob