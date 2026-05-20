package com.hackathon.bffbobathon.domain.entity;

import lombok.*;

/**
 * Entidade que representa um conteúdo educacional no sistema.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Content {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
}

// Made with Bob
