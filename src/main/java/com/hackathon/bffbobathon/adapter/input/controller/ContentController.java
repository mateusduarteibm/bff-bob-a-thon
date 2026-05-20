package com.hackathon.bffbobathon.adapter.input.controller;

import com.hackathon.bffbobathon.adapter.input.dto.ContentDTO;
import com.hackathon.bffbobathon.adapter.input.dto.CreateContentRequest;
import com.hackathon.bffbobathon.adapter.input.mapper.ContentMapper;
import com.hackathon.bffbobathon.domain.entity.Content;
import com.hackathon.bffbobathon.domain.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de conteúdos educacionais.
 */
@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Contents", description = "API para gerenciamento de conteúdos educacionais")
public class ContentController {

    private final ContentService contentService;
    private final ContentMapper contentMapper;

    /**
     * Lista todos os conteúdos.
     */
    @GetMapping
    @Operation(summary = "Listar conteúdos", description = "Retorna todos os conteúdos disponíveis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<List<ContentDTO>> getAllContents() {
        log.info("GET /contents - Listando todos os conteúdos");
        
        List<Content> contents = contentService.getAllContents();
        List<ContentDTO> response = contentMapper.toDTOList(contents);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Busca um conteúdo por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar conteúdo por ID", description = "Retorna um conteúdo específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteúdo encontrado"),
            @ApiResponse(responseCode = "404", description = "Conteúdo não encontrado")
    })
    public ResponseEntity<ContentDTO> getContentById(
            @Parameter(description = "ID do conteúdo") @PathVariable Long id) {
        log.info("GET /contents/{} - Buscando conteúdo", id);
        
        Content content = contentService.getContentById(id);
        ContentDTO response = contentMapper.toDTO(content);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Cria um novo conteúdo.
     */
    @PostMapping
    @Operation(summary = "Criar novo conteúdo", description = "Cria um novo conteúdo educacional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conteúdo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ContentDTO> createContent(
            @Valid @RequestBody CreateContentRequest request) {
        log.info("POST /contents - Criando novo conteúdo: {}", request.getName());
        
        Content content = contentService.createContent(request);
        ContentDTO response = contentMapper.toDTO(content);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Deleta um conteúdo.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar conteúdo", description = "Remove um conteúdo do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conteúdo deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conteúdo não encontrado")
    })
    public ResponseEntity<Void> deleteContent(
            @Parameter(description = "ID do conteúdo") @PathVariable Long id) {
        log.info("DELETE /contents/{} - Deletando conteúdo", id);
        
        contentService.deleteContent(id);
        
        return ResponseEntity.noContent().build();
    }
}

// Made with Bob
