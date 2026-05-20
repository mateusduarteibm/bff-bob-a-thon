package com.hackathon.bffbobathon.adapter.output.memory;

import com.hackathon.bffbobathon.domain.entity.Content;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositório em memória para armazenamento de conteúdos.
 * Usa ConcurrentHashMap para thread-safety.
 */
@Repository
public class ContentRepository {

    private final Map<Long, Content> contents = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ContentRepository() {
        // Inicializa com dados mockados
        initializeMockData();
    }

    private void initializeMockData() {
        save(Content.builder()
                .name("Introdução ao Spring Boot")
                .description("Aprenda os fundamentos do Spring Boot e crie sua primeira aplicação REST")
                .imageUrl("https://spring.io/img/projects/spring-boot.svg")
                .build());

        save(Content.builder()
                .name("Angular Avançado")
                .description("Domine técnicas avançadas de Angular incluindo RxJS, Signals e arquitetura")
                .imageUrl("https://angular.io/assets/images/logos/angular/angular.svg")
                .build());

        save(Content.builder()
                .name("Docker para Desenvolvedores")
                .description("Containerize suas aplicações e aprenda Docker do básico ao avançado")
                .imageUrl("https://www.docker.com/wp-content/uploads/2022/03/vertical-logo-monochromatic.png")
                .build());

        save(Content.builder()
                .name("Microserviços com Java")
                .description("Construa arquiteturas de microserviços escaláveis usando Java e Spring Cloud")
                .imageUrl("https://spring.io/img/projects/spring-cloud.svg")
                .build());

        save(Content.builder()
                .name("Git e GitHub Essencial")
                .description("Controle de versão profissional com Git e colaboração no GitHub")
                .imageUrl("https://git-scm.com/images/logos/downloads/Git-Icon-1788C.png")
                .build());
    }

    /**
     * Salva um novo conteúdo.
     */
    public Content save(Content content) {
        if (content.getId() == null) {
            content.setId(idGenerator.getAndIncrement());
        }
        contents.put(content.getId(), content);
        return content;
    }

    /**
     * Busca um conteúdo por ID.
     */
    public Optional<Content> findById(Long id) {
        return Optional.ofNullable(contents.get(id));
    }

    /**
     * Lista todos os conteúdos.
     */
    public List<Content> findAll() {
        return new ArrayList<>(contents.values());
    }

    /**
     * Deleta um conteúdo por ID.
     */
    public void deleteById(Long id) {
        contents.remove(id);
    }

    /**
     * Verifica se um conteúdo existe.
     */
    public boolean existsById(Long id) {
        return contents.containsKey(id);
    }
}

// Made with Bob
