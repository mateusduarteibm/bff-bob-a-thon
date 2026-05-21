package com.hackathon.bffbobathon.adapter.input.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO para representação de um favorito.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDTO {

    private Long id;
    private Long userId;
    private Long contentId;
    private ContentDTO content;
    private LocalDateTime createdAt;
}

// Made with Bob