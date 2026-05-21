package com.hackathon.bffbobathon.adapter.input.mapper;

import com.hackathon.bffbobathon.adapter.input.dto.ContentDTO;
import com.hackathon.bffbobathon.adapter.input.dto.FavoriteDTO;
import com.hackathon.bffbobathon.domain.entity.Content;
import com.hackathon.bffbobathon.domain.entity.Favorite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para conversão entre Favorite e FavoriteDTO.
 */
@Component
@RequiredArgsConstructor
public class FavoriteMapper {

    private final ContentMapper contentMapper;

    /**
     * Converte Favorite para FavoriteDTO.
     */
    public FavoriteDTO toDTO(Favorite favorite) {
        if (favorite == null) {
            return null;
        }

        return FavoriteDTO.builder()
                .id(favorite.getId())
                .userId(favorite.getUserId())
                .contentId(favorite.getContentId())
                .createdAt(favorite.getCreatedAt())
                .build();
    }

    /**
     * Converte Favorite para FavoriteDTO incluindo dados do Content.
     */
    public FavoriteDTO toDTOWithContent(Favorite favorite, Content content) {
        if (favorite == null) {
            return null;
        }

        ContentDTO contentDTO = content != null ? contentMapper.toDTO(content) : null;

        return FavoriteDTO.builder()
                .id(favorite.getId())
                .userId(favorite.getUserId())
                .contentId(favorite.getContentId())
                .content(contentDTO)
                .createdAt(favorite.getCreatedAt())
                .build();
    }

    /**
     * Converte lista de Favorite para lista de FavoriteDTO.
     */
    public List<FavoriteDTO> toDTOList(List<Favorite> favorites) {
        if (favorites == null) {
            return List.of();
        }

        return favorites.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

// Made with Bob