package com.hackathon.bffbobathon.adapter.input.mapper;

import com.hackathon.bffbobathon.adapter.input.dto.ContentDTO;
import com.hackathon.bffbobathon.domain.entity.Content;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper para conversão entre entidade Content e seus DTOs.
 */
@Mapper(componentModel = "spring")
public interface ContentMapper {

    /**
     * Converte entidade Content para ContentDTO.
     */
    ContentDTO toDTO(Content content);

    /**
     * Converte lista de entidades Content para lista de ContentDTO.
     */
    List<ContentDTO> toDTOList(List<Content> contents);
}

// Made with Bob
