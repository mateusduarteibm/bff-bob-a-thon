package com.hackathon.bffbobathon.domain.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidade que representa um conteúdo favoritado por um usuário.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Favorite {

    private Long id;
    private Long userId;
    private Long contentId;
    private LocalDateTime createdAt;
}

// Made with Bob