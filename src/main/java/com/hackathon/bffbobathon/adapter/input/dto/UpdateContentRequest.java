package com.hackathon.bffbobathon.adapter.input.dto;

import com.hackathon.bffbobathon.domain.entity.ContentType;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para requisição de atualização de conteúdo existente.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateContentRequest {

    @Size(max = 200, message = "Título deve ter no máximo 200 caracteres")
    private String title;

    @Size(max = 5000, message = "Descrição deve ter no máximo 5000 caracteres")
    private String description;

    private ContentType type;

    @Size(max = 500, message = "URL deve ter no máximo 500 caracteres")
    @Pattern(regexp = "^https?://.*", message = "URL deve começar com http:// ou https://")
    private String url;

    @Size(max = 500, message = "URL da thumbnail deve ter no máximo 500 caracteres")
    @Pattern(regexp = "^https?://.*", message = "URL da thumbnail deve começar com http:// ou https://")
    private String thumbnailUrl;

    @Min(value = 1, message = "Duração deve ser no mínimo 1 minuto")
    @Max(value = 10000, message = "Duração deve ser no máximo 10000 minutos")
    private Integer durationMinutes;

    private Boolean active;
}

// Made with Bob
