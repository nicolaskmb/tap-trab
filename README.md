
# Documentação do Projeto: Sistema de Chat em Microsserviços

## Contextualização do projeto

Este projeto consiste no desenvolvimento de uma arquitetura de **Back-end distribuída baseada em microsserviços**, projetada para suportar uma aplicação de mensagens. A arquitetura foi desenhada seguindo os princípios do **Domain-Driven Design (DDD)**, garantindo que a complexidade do negócio esteja isolada de detalhes de infraestrutura. O sistema resolve desafios de sistemas distribuídos, como descoberta de serviços, roteamento dinâmico e autenticação centralizada.

## O que é

Trata-se de uma API robusta para um sistema de **Chat 1-para-1 (Direct Messaging)**. A aplicação permite que usuários se cadastrem, realizem login para obter credenciais de segurança e troquem mensagens de texto com outros usuários. O sistema é composto por serviços autônomos que operam em containers Docker.

## O que motivou o desenvolvimento

O principal objetivo foi criar um sistema escalável e modular, aplicando padrões modernos de engenharia de software:

1.  **Desacoplamento:** Separação clara entre o domínio de Identidade (Auth) e o domínio de Mensagens (Chat).
    
2.  **Segurança em Microsserviços:** Implementação de um API Gateway que centraliza a validação de tokens JWT, repassando o contexto do usuário para os serviços internos de forma transparente.
    
3.  **Infraestrutura como Código:** Uso de Docker para garantir que todo o ambiente (bancos de dados, serviços, proxy) suba com um único comando.
    

## Tecnologias utilizadas

O projeto utiliza uma stack moderna focada no ecossistema Java/Spring:

-   **Linguagem:** Java 17
    
-   **Framework:** Spring Boot 3.5.7
    
-   **Ecossistema Cloud:** Spring Cloud Gateway, Spring Cloud Netflix Eureka
    
-   **Banco de Dados:** H2 Database (Em memória, isolado por serviço)
    
-   **Segurança:** JWT (JSON Web Token) e Spring Security
    
-   **Infraestrutura:** Docker e Docker Compose
    
-   **Proxy Reverso:** Nginx
    
-   **Ferramentas:** Maven, Lombok, Swagger UI (OpenAPI)
    

## Serviços desenvolvidos

### 1. Service Discovery (Eureka)

Funciona como o catálogo de endereços do sistema. Permite que o Gateway encontre os serviços de `auth` e `chat` dinamicamente, sem depender de IPs fixos.

### 2. API Gateway

É a porta de entrada da aplicação (atrás do Nginx).

-   Gerencia o roteamento das requisições.
    
-   Possui um **Filtro de Autorização** que intercepta o Token JWT, valida a assinatura e injeta o ID do usuário no cabeçalho `X-User-Id` para os serviços internos.
    

### 3. Auth Service

Responsável pelo domínio de usuários e autenticação.

-   Endpoints para registro e login (geração de token).
    
-   Gerenciamento de perfil (alteração de nome).
    

### 4. Chat Service

Responsável pelo domínio de mensagens.

-   Gerencia a criação de conversas entre dois usuários.
    
-   Persiste e recupera o histórico de mensagens.
    

### 5. Nginx

Servidor web que atua como Proxy Reverso na porta 80, encaminhando o tráfego externo para o container do Gateway.

## Endpoints

### Autenticação e Usuários

-   `POST /auth/users` - Cria um novo usuário.
    
-   `POST /auth/auth/login/password` - Realiza login e retorna o Token de Acesso.
    
-   `PUT /users/me` - Atualiza o nome do usuário logado (Requer Token).
    
-   `GET /users` - Lista todos os usuários cadastrados.
    

### Chat

-   `GET /chat/conversations` - Lista as conversas do usuário.
    
-   `POST /chat/conversations` - Inicia uma nova conversa com outro usuário.
    
-   `POST /chat/conversations/{id}/messages` - Envia uma mensagem.
    
-   `GET /chat/conversations/{id}/messages` - Lê o histórico de mensagens.
    

## Como Executar o Projeto

Para colocar a aplicação em funcionamento, siga os passos abaixo:

1.  **Faça um Fork ou Clone o repositório**
    
2.  **Crie um Codespace (Opcional)**
    
3.  **Execute o projeto com Docker Compose utilizando o terminal** `docker compose up --build`
    
    _Aguarde até que todos os serviços (service-discovery, auth-service, chat, gateway-service) estejam iniciados e o Nginx esteja pronto._
    
4.  **Acesse a aplicação:**
    
    -   **Chat Service Swagger:** `http://localhost:8081/swagger-ui/index.html`
        
    -   **Auth Service Swagger:** `http://localhost:8085/swagger-ui/index.html`