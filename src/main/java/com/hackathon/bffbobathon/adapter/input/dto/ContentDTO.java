package com.hackathon.bffbobathon.adapter.input.dto;

import lombok.*;

/**
 * DTO para representação de um conteúdo.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentDTO {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
}

// Made with Bob
