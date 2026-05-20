package com.hackathon.bffbobathon.domain.service;

import com.hackathon.bffbobathon.adapter.input.dto.CreateContentRequest;
import com.hackathon.bffbobathon.adapter.output.memory.ContentRepository;
import com.hackathon.bffbobathon.domain.entity.Content;
import com.hackathon.bffbobathon.domain.exception.ContentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço de domínio para gerenciamento de conteúdos.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContentService {

    private final ContentRepository contentRepository;

    /**
     * Cria um novo conteúdo.
     */
    public Content createContent(CreateContentRequest request) {
        log.info("Criando novo conteúdo: {}", request.getName());
        
        Content content = Content.builder()
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();
        
        Content savedContent = contentRepository.save(content);
        log.info("Conteúdo criado com sucesso. ID: {}", savedContent.getId());
        
        return savedContent;
    }

    /**
     * Busca um conteúdo por ID.
     */
    public Content getContentById(Long id) {
        log.debug("Buscando conteúdo por ID: {}", id);
        
        return contentRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException(id));
    }

    /**
     * Lista todos os conteúdos.
     */
    public List<Content> getAllContents() {
        log.debug("Listando todos os conteúdos");
        return contentRepository.findAll();
    }

    /**
     * Deleta um conteúdo.
     */
    public void deleteContent(Long id) {
        log.info("Deletando conteúdo ID: {}", id);
        
        if (!contentRepository.existsById(id)) {
            throw new ContentNotFoundException(id);
        }
        
        contentRepository.deleteById(id);
        log.info("Conteúdo deletado com sucesso. ID: {}", id);
    }
}

// Made with Bob
