package com.hackathon.bffbobathon.adapter.input.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para requisição de criação de novo conteúdo.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateContentRequest {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    private String name;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String description;

    @NotBlank(message = "URL da imagem é obrigatória")
    @Size(max = 500, message = "URL da imagem deve ter no máximo 500 caracteres")
    @Pattern(regexp = "^https?://.*", message = "URL da imagem deve começar com http:// ou https://")
    private String imageUrl;
}

// Made with Bob
