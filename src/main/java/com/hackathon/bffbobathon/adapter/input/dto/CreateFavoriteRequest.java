package com.hackathon.bffbobathon.adapter.input.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Request para criação de um favorito.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFavoriteRequest {

    @NotNull(message = "User ID é obrigatório")
    private Long userId;

    @NotNull(message = "Content ID é obrigatório")
    private Long contentId;
}

// Made with Bob