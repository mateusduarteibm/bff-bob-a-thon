# BFF Bob-a-thon - Backend API

Backend for Frontend (BFF) para gerenciamento de conteúdos educacionais do Bob-a-thon.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Executando a Aplicação](#executando-a-aplicação)
- [Executando os Testes](#executando-os-testes)
- [Documentação da API](#documentação-da-api)
- [Endpoints](#endpoints)
- [Health Check](#health-check)
- [Configurações](#configurações)

## 🎯 Sobre o Projeto

O BFF Bob-a-thon é uma API REST desenvolvida para gerenciar conteúdos educacionais, incluindo vídeos, artigos, tutoriais, documentações, cursos e podcasts. A aplicação oferece funcionalidades completas de CRUD, busca, filtros e controle de visualizações.

### Funcionalidades Principais

- ✅ CRUD de conteúdos (Create, Read, Delete)
- ✅ Listagem de todos os conteúdos
- ✅ Busca de conteúdo por ID
- ✅ **Sistema de Favoritos** - Marcar/desmarcar conteúdos como favoritos
- ✅ Validação de dados
- ✅ Tratamento de erros padronizado
- ✅ Documentação Swagger/OpenAPI
- ✅ Testes unitários e de integração

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.2.5**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
  - Spring Actuator
- **H2 Database** (em memória)
- **Lombok** (redução de boilerplate)
- **MapStruct** (mapeamento de objetos)
- **Springdoc OpenAPI** (documentação Swagger)
- **JUnit 5** e **Mockito** (testes)
- **Maven** (gerenciamento de dependências)

## 🏗️ Arquitetura

O projeto segue os princípios da **Arquitetura Hexagonal (Ports & Adapters)** com separação clara de responsabilidades:

```
src/main/java/com/hackathon/bffbobathon/
├── adapter/
│   ├── input/              # Camada de entrada (Controllers, DTOs)
│   │   ├── controller/     # REST Controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── mapper/         # Mapeadores (MapStruct)
│   │   └── exception/      # Handlers de exceção
│   └── output/
│       └── database/       # Camada de persistência (Repositories)
├── domain/
│   ├── entity/             # Entidades de domínio
│   ├── service/            # Serviços de domínio
│   └── exception/          # Exceções de domínio
├── config/                 # Configurações Spring
└── Application.java        # Classe principal
```

### Camadas

- **Adapter Input**: Recebe requisições HTTP e converte para objetos de domínio
- **Domain**: Contém a lógica de negócio e regras do domínio
- **Adapter Output**: Persiste dados no banco de dados

## 📦 Pré-requisitos

- Java 17 ou superior
- Maven 3.8 ou superior
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code)

## 🔧 Instalação

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd bff-bob-a-thon
```

2. Instale as dependências:
```bash
mvn clean install
```

## ▶️ Executando a Aplicação

### 🌐 Aplicação em Produção (AWS)

A aplicação está deployada e disponível em:

- **API Base**: `https://bff-bob-a-thon-env.eba-kipvpqnj.us-east-1.elasticbeanstalk.com`
- **Swagger UI**: `https://bff-bob-a-thon-env.eba-kipvpqnj.us-east-1.elasticbeanstalk.com/api/swagger-ui/index.html`
- **OpenAPI Spec**: `https://bff-bob-a-thon-env.eba-kipvpqnj.us-east-1.elasticbeanstalk.com/api/v3/api-docs`

> ⚠️ **Certificado autoassinado**: o endpoint HTTPS utiliza um certificado autoassinado. Na primeira vez que acessar a API pelo browser, será necessário aceitar o aviso de segurança ("Avançado → Prosseguir"). Após isso, o frontend em HTTPS (GitHub Pages) consegue se comunicar normalmente com a API.

### 🔒 Configuração HTTPS

O ambiente AWS Elastic Beanstalk está configurado com HTTPS via:

- **Load Balancer (ALB)**: listener na porta 443 com certificado importado no ACM
- **Instância EC2**: nginx configurado com certificado autoassinado via `.platform/hooks/predeploy/01_gen_ssl.sh`
- **Nginx**: servidor HTTPS em `.platform/nginx/conf.d/https.conf` fazendo proxy reverso para a aplicação na porta 5000

O deploy é feito enviando apenas o JAR pré-compilado e os arquivos `.platform/`:

```bash
# Build do JAR
mvn clean package -DskipTests
cp target/bff-bob-a-thon-1.0.0.jar .

# Deploy no Elastic Beanstalk
eb deploy
```

📖 **Veja o guia completo de deploy e testes em [DEPLOYMENT.md](DEPLOYMENT.md)**

### Modo Desenvolvimento (Local)

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080/api`

### Build para Produção

```bash
mvn clean package
java -jar target/bff-bob-a-thon-1.0.0.jar
```

## 🧪 Executando os Testes

### Todos os Testes

```bash
mvn test
```

### Testes com Relatório de Cobertura

```bash
mvn clean test
```

O relatório de cobertura estará disponível em: `target/site/jacoco/index.html`

### Testes de Integração

```bash
mvn verify
```

## 📚 Documentação da API

A documentação interativa da API está disponível via Swagger UI:

- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api/v3/api-docs

### Console H2 Database

Para acessar o console do banco de dados H2:

- **URL**: http://localhost:8080/api/h2-console
- **JDBC URL**: `jdbc:h2:mem:bobathon`
- **Username**: `sa`
- **Password**: (deixe em branco)

## 🔌 Endpoints

### Base URL
```
http://localhost:8080/api
```

### Conteúdos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/contents` | Listar todos os conteúdos |
| GET | `/contents/{id}` | Buscar conteúdo por ID |
| POST | `/contents` | Criar novo conteúdo |
| DELETE | `/contents/{id}` | Deletar conteúdo |

### Favoritos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/favorites` | Adicionar conteúdo aos favoritos |
| GET | `/favorites/user/{userId}` | Listar favoritos do usuário |
| GET | `/favorites/user/{userId}/content/{contentId}` | Verificar se está favoritado |
| GET | `/favorites/user/{userId}/count` | Contar favoritos do usuário |
| GET | `/favorites/{id}` | Buscar favorito por ID |
| DELETE | `/favorites/{id}` | Remover favorito por ID |
| DELETE | `/favorites/user/{userId}/content/{contentId}` | Remover favorito específico |

📖 **Documentação completa da API de Favoritos**: [FAVORITES_API.md](FAVORITES_API.md)

### Tipos de Conteúdo

- `VIDEO` - Vídeos
- `ARTICLE` - Artigos
- `TUTORIAL` - Tutoriais
- `DOCUMENTATION` - Documentação
- `COURSE` - Cursos
- `PODCAST` - Podcasts

### Exemplos de Requisições

#### Criar Conteúdo

```bash
POST /api/contents
Content-Type: application/json

{
  "name": "Introdução ao Spring Boot",
  "description": "Tutorial completo sobre Spring Boot para iniciantes",
  "type": "VIDEO",
  "url": "https://youtube.com/watch?v=exemplo"
}
```

#### Resposta (201 Created)

```json
{
  "id": 1,
  "name": "Introdução ao Spring Boot",
  "description": "Tutorial completo sobre Spring Boot para iniciantes",
  "type": "VIDEO",
  "url": "https://youtube.com/watch?v=exemplo",
  "createdAt": "2024-05-20T12:00:00"
}
```

#### Listar Todos os Conteúdos

```bash
GET /api/contents
```

#### Buscar Conteúdo por ID

```bash
GET /api/contents/1
```

#### Deletar Conteúdo

```bash
DELETE /api/contents/1
```

Resposta: `204 No Content`

### Tratamento de Erros

A API retorna erros padronizados:

#### Erro 404 - Not Found

```json
{
  "timestamp": "2024-05-20T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Conteúdo com ID 999 não encontrado"
}
```

#### Erro 400 - Validation Error

```json
{
  "timestamp": "2024-05-20T12:00:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Erro de validação nos campos",
  "errors": {
    "title": "Título é obrigatório",
    "url": "URL deve começar com http:// ou https://"
  }
}
```

## 🔍 Health Check

A aplicação expõe endpoints de health check via Spring Actuator:

```bash
GET /api/actuator/health
```

Resposta:
```json
{
  "status": "UP"
}
```

## 🛠️ Configurações

As configurações da aplicação estão em `src/main/resources/application.yml`:

- **Porta**: 8080
- **Context Path**: /api
- **Banco de Dados**: H2 em memória
- **Logs**: DEBUG para o pacote da aplicação

## 📝 Licença

Este projeto está sob a licença MIT.

## 👥 Equipe

Desenvolvido pela equipe Bob-a-thon para o Hackathon.

---

**Documentação gerada em:** Maio 2024
