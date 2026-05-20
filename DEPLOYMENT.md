# Guia de Deploy - BFF Bob-a-thon

## ✅ Aplicação Deployada com Sucesso!

### 🌐 URLs da Aplicação

- **API Base URL**: `http://bff-bob-a-thon-env.eba-kipvpqnj.us-east-1.elasticbeanstalk.com`
- **Swagger UI**: `http://bff-bob-a-thon-env.eba-kipvpqnj.us-east-1.elasticbeanstalk.com/api/swagger-ui/index.html`
- **OpenAPI Spec**: `http://bff-bob-a-thon-env.eba-kipvpqnj.us-east-1.elasticbeanstalk.com/api/v3/api-docs`

> ⚠️ **Atenção**: A API está atualmente em HTTP. Para usar com frontend HTTPS (GitHub Pages), você precisa habilitar HTTPS. Veja o guia completo em [HTTPS_SETUP.md](HTTPS_SETUP.md).

### 📋 Endpoints Disponíveis

#### 1. Listar Todos os Conteúdos
```http
GET /api/contents
```

**Resposta de Exemplo:**
```json
[
  {
    "id": "1",
    "name": "Introdução ao Bob",
    "description": "Aprenda os conceitos básicos do Bob AI Assistant",
    "imageUrl": "https://example.com/images/intro-bob.jpg"
  },
  {
    "id": "2",
    "name": "Comandos Avançados",
    "description": "Domine os comandos avançados do Bob",
    "imageUrl": "https://example.com/images/advanced-commands.jpg"
  }
]
```

#### 2. Buscar Conteúdo por ID
```http
GET /api/contents/{id}
```

**Exemplo:**
```http
GET /api/contents/1
```

**Resposta:**
```json
{
  "id": "1",
  "name": "Introdução ao Bob",
  "description": "Aprenda os conceitos básicos do Bob AI Assistant",
  "imageUrl": "https://example.com/images/intro-bob.jpg"
}
```

#### 3. Criar Novo Conteúdo
```http
POST /api/contents
Content-Type: application/json

{
  "name": "Novo Conteúdo",
  "description": "Descrição do novo conteúdo",
  "imageUrl": "https://example.com/images/new-content.jpg"
}
```

**Resposta:**
```json
{
  "id": "6",
  "name": "Novo Conteúdo",
  "description": "Descrição do novo conteúdo",
  "imageUrl": "https://example.com/images/new-content.jpg"
}
```

#### 4. Deletar Conteúdo
```http
DELETE /api/contents/{id}
```

**Exemplo:**
```http
DELETE /api/contents/1
```

**Resposta:** `204 No Content`

### 🧪 Testando a API

#### Usando Insomnia/Postman
1. Importe a coleção do Swagger: `http://bff-bob-a-thon-env.eba-kipvpqnj.us-east-1.elasticbeanstalk.com/api/v3/api-docs`
2. Configure a base URL: `http://bff-bob-a-thon-env.eba-kipvpqnj.us-east-1.elasticbeanstalk.com`
3. Teste os endpoints

#### Usando Swagger UI
1. Acesse: `http://bff-bob-a-thon-env.eba-kipvpqnj.us-east-1.elasticbeanstalk.com/api/swagger-ui/index.html`
2. Clique em "Try it out" em qualquer endpoint
3. Execute as requisições diretamente no navegador

### 🚀 Processo de Deploy

#### Pré-requisitos
- AWS CLI configurado
- EB CLI instalado (`pip install awsebcli`)
- Java 17
- Maven 3.6+

#### Passos para Deploy

1. **Build da aplicação:**
```bash
mvn clean package -DskipTests
```

2. **Copiar JAR para raiz:**
```bash
copy target\bff-bob-a-thon-1.0.0.jar .
```

3. **Deploy no Elastic Beanstalk:**
```bash
eb deploy
```

4. **Verificar status:**
```bash
eb status
```

5. **Abrir aplicação no navegador:**
```bash
eb open
```

### 📦 Arquivos Importantes para Deploy

- **Procfile**: Define o comando de inicialização
```
web: java -jar bff-bob-a-thon-1.0.0.jar --server.port=5000
```

- **.ebignore**: Define o que NÃO enviar para o EB
```
*
!bff-bob-a-thon-1.0.0.jar
!Procfile
```

### 🔧 Configuração do Ambiente

- **Platform**: Corretto 17 running on 64bit Amazon Linux 2023
- **Region**: us-east-1
- **Instance Type**: t2.micro (Free Tier)
- **Health**: Green ✅
- **Status**: Ready ✅

### 📊 Dados Mock Pré-carregados

A aplicação vem com 5 conteúdos pré-carregados em memória:

1. **Introdução ao Bob** - Conceitos básicos
2. **Comandos Avançados** - Comandos avançados
3. **Integração com IDEs** - Integração com VS Code
4. **Melhores Práticas** - Boas práticas de uso
5. **Troubleshooting** - Resolução de problemas

### 🔄 Atualizando a Aplicação

1. Faça as alterações no código
2. Rebuild: `mvn clean package -DskipTests`
3. Copie o novo JAR: `copy target\bff-bob-a-thon-1.0.0.jar .`
4. Deploy: `eb deploy`

### 📝 Logs

Para visualizar os logs da aplicação:
```bash
eb logs
```

Para logs em tempo real:
```bash
eb logs --stream
```

### 🛑 Encerrando o Ambiente

Para economizar custos, você pode encerrar o ambiente quando não estiver usando:
```bash
eb terminate bff-bob-a-thon-env
```

**⚠️ Atenção**: Isso irá deletar todos os recursos do ambiente!

### 💰 Custos

- **t2.micro**: Elegível para Free Tier (750 horas/mês grátis no primeiro ano)
- **Load Balancer**: ~$18/mês (se habilitado)
- **S3**: Mínimo (apenas para armazenar versões da aplicação)

### 🎯 Próximos Passos

1. ✅ Backend Java deployado e funcionando
2. ⏳ Criar frontend Angular
3. ⏳ Integrar frontend com backend
4. ⏳ Adicionar autenticação (se necessário)
5. ⏳ Configurar domínio customizado (opcional)

---

**Desenvolvido para o Hackathon Bob-a-thon** 🚀