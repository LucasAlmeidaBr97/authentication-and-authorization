# 🔐 Authentication and Authorization API

Uma solução completa de **Autenticação e Autorização Stateless** desenvolvida com **Java 21**, **Spring Boot 3** e **Spring Security**, utilizando **Tokens JWT (JSON Web Token)** e persistência em banco de dados relacional **MySQL** com migrações automatizadas via **Flyway**.

---

## 🛠️ Tecnologias Utilizadas e Suas Versões

- **Java**: `21` (Open JDK / Amazon Corretto 21)
- **Spring Boot**: `3.5.14`
- **Spring Security**: `3.5.14` (Autenticação e controle de acesso)
- **Spring Data JPA / Hibernate**: `3.5.14` (Mapeamento Objeto-Relacional)
- **Spring Boot Starter Validation**: `3.5.14` (Validação de DTOs com Jakarta Validation)
- **Spring Boot Starter Actuator**: `3.5.14` (Monitoramento de saúde e métricas)
- **com.auth0:java-jwt**: `4.4.0` (Geração, verificação e decodificação de tokens JWT)
- **MySQL Connector / J**: `runtime` (Driver de conexão com banco de dados MySQL 8.x)
- **Flyway Core & Flyway MySQL**: `3.5.14` (Gerenciamento e versionamento de esquemas de banco de dados)
- **Lombok**: `1.18.x` (Injeção de boilerplates, getters, setters e builder)
- **Apache Maven**: `3.9+` (Gerenciamento de dependências e build)

---

## 🎯 Objetivo do Projeto

O objetivo principal deste projeto é fornecer uma **arquitetura de referência robusta, segura e reutilizável** para autenticação e autorização de usuários em APIs RESTful. 

A aplicação foi projetada para resolver os seguintes desafios de segurança em ecossistemas backend modernizados:
1. **Autenticação Stateless**: Eliminar a necessidade de armazenar estados de sessão no servidor backend, aumentando a escalabilidade horizontal da aplicação.
2. **Segurança de Credenciais**: Armazenar credenciais de acesso de forma segura através do algoritmo de hash adaptativo **BCrypt**.
3. **Controle de Acesso por Tokens**: Garantir que requisições a rotas protegidas sejam validadas por um token JWT assinado criptograficamente.
4. **Padronização e Boas Práticas**: Servir como modelo (boilerplate) para novos microsserviços ou aplicações monolíticas que demandem um sistema confiável de gestão de identidades.

---

## ✨ Funcionalidades

- 📝 **Registro de Usuários (`POST /auth/register`)**: Permite o cadastro de novos usuários com validação automatizada de campos (nome, e-mail e senha) e criptografia imediata da senha.
- 🔑 **Autenticação / Login (`POST /auth/login`)**: Autentica o usuário com base no e-mail e senha, retornando um token JWT assinado válido por 24 horas.
- 🛡️ **Filtro de Segurança JWT (`SecurityFilter`)**: Intercepta todas as requisições HTTP para validar o token contido no cabeçalho `Authorization: Bearer <token>` e preencher o contexto de segurança (`SecurityContextHolder`).
- 🔐 **Proteção de Endpoints**: Configuração do Spring Security limitando acesso a rotas privadas apenas a requisições com tokens válidos.
- 🧪 **Endpoint Protegido para Testes (`GET /test`)**: Rota de teste acessível exclusivamente por requisições autenticadas.
- 🗄️ **Migração de Banco de Dados Automatizada (Flyway)**: Execução automática de scripts de banco de dados (`V1__create_table_user.sql`) ao iniciar a aplicação.
- ✔️ **Validação Transparente de Dados de Entrada**: Retorno amigável de erros de validação caso os campos obrigatórios não sejam informados.

---

## 🚀 Começando

Siga as instruções abaixo para obter uma cópia local do projeto em execução em sua máquina.

### 📋 Pré-requisitos

Antes de iniciar, certifique-se de ter instalado em sua máquina:
- [Java JDK 21](https://www.oracle.com/java/technologies/downloads/#java21) ou superior configurado no `PATH`.
- [MySQL Server 8.0+](https://dev.mysql.com/downloads/installer/) rodando localmente ou via container Docker.
- [Git](https://git-scm.com/) para clonar o repositório.
- Um cliente HTTP para teste dos endpoints (ex.: [Postman](https://www.postman.com/), [Insomnia](https://insomnia.rest/) ou cURL).

### ⚙️ Instalação e Execução

1. **Clonar o repositório**:
   ```bash
   git clone https://github.com/LucasAlmeidaBr97/authentication-and-authorization.git
   cd authentication-and-authorization
   ```

2. **Configurar o Banco de Dados MySQL**:
   Crie o banco de dados no seu servidor MySQL:
   ```sql
   CREATE DATABASE authentication_and_authorization;
   ```
   Caso necessário, altere o usuário e senha do banco no arquivo `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/authentication_and_authorization
   spring.datasource.username=root
   spring.datasource.password=sua_senha_aqui
   ```

3. **Compilar o projeto com o Maven Wrapper**:
   - **Windows**:
     ```powershell
     .\mvnw.cmd clean compile
     ```
   - **Linux / macOS**:
     ```bash
     ./mvnw clean compile
     ```

4. **Executar a aplicação**:
   - **Windows**:
     ```powershell
     .\mvnw.cmd spring-boot:run
     ```
   - **Linux / macOS**:
     ```bash
     ./mvnw spring-boot:run
     ```

   A aplicação estará disponível em `http://localhost:8080`.

---

## 🏗️ Descrição Técnica

### 🏛️ Arquitetura e Organização

O projeto adota a **Layered Architecture (Arquitetura em Camadas)** para separação clara de responsabilidades:

```text
src/main/java/dev/lucas_a/authentication_and_authorization/
├── AuthenticationAndAuthorizationApplication.java   # Classe principal de inicialização
├── config/                                         # Configurações de Segurança e JWT
│   ├── AuthConfig.java                             # Implementação do UserDetailsService
│   ├── JWTUserData.java                            # DTO interno para dados decodificados do JWT
│   ├── SecurityConfig.java                         # Bean SecurityFilterChain, PasswordEncoder e CORS/CSRF
│   ├── SecurityFilter.java                         # Filtro por requisição para interceptar o Token Bearer
│   └── TokenConfig.java                            # Serviço de geração e validação de tokens JWT (HMAC256)
├── controller/                                     # Endpoints REST expostos
│   ├── AuthController.java                         # Endpoints /auth/login e /auth/register
│   └── TestController.java                         # Endpoint /test de validação da segurança
├── dto/                                            # Objetos de Transferência de Dados (Java Records)
│   ├── request/
│   │   ├── LoginRequest.java                       # Payload de login (email, password)
│   │   └── RegisterUserRequest.java                # Payload de registro (name, email, password)
│   └── response/
│       ├── LoginResponse.java                      # Retorno do login (token JWT)
│       └── RegisterUserResponse.java               # Retorno de registro (name, email)
├── entity/                                         # Mapeamento Objeto-Relacional (JPA Entities)
│   └── User.java                                   # Entidade 'users' implementando UserDetails
└── repository/                                     # Interfaces de acesso a dados (Spring Data JPA)
    └── UserRepository.java                         # Consultas à tabela de usuários por e-mail
```

### 💡 Tecnologias e Conceitos de Design

1. **Autenticação Stateless via JWT (JSON Web Token)**:
   - Utilização do algoritmo **HMAC256** para assinatura do token JWT.
   - Inserção de claims customizados no token (ex: `userId` e `subject` contendo o e-mail do usuário).
   - Validação a cada requisição no `SecurityFilter` antes do Spring Security autorizar o acesso.
2. **Uso de Java 21 Records para DTOs**:
   - DTOs definidos como `record` garantem imutabilidade nativa e código conciso.
3. **Criptografia com BCrypt**:
   - `BCryptPasswordEncoder` utilizado para hashing de senhas antes do armazenamento no banco de dados.
4. **Gerenciamento de Esquema com Flyway**:
   - As tabelas são criadas e atualizadas através de scripts versionados SQL localizados em `src/main/resources/db/migration/`.
5. **Tratamento e Validação de Dados**:
   - Bean Validation com anotações `@NotEmpty` e `@Valid` no `AuthController` para barrar requisições inconsistentes antes de atingirem a camada de negócio.

### 📋 Requisitos Funcionais

- **RF-001 (Cadastro de Usuários)**: O sistema deve permitir o cadastro de novos usuários com nome, e-mail e senha.
- **RF-002 (Criptografia de Senha)**: As senhas dos usuários devem ser criptografadas com hash BCrypt antes de persistidas no banco.
- **RF-003 (Autenticação)**: O sistema deve autenticar o usuário ao validar o e-mail e a senha fornecidos.
- **RF-004 (Geração de JWT)**: Após autenticação com sucesso no endpoint `/auth/login`, o sistema deve retornar um token JWT válido por 24 horas.
- **RF-005 (Validação de Token em Requisições)**: O sistema deve interceptar requisições em rotas protegidas (como `GET /test`) e validar o token fornecido no cabeçalho `Authorization: Bearer <token>`.
- **RF-006 (Controle de Erro na Autenticação)**: Caso o token fornecido seja inválido, ausente ou expirado, o sistema deve recusar o acesso com código de erro correspondente.

---

## 🔮 Evolução e Roadmap

Futuras melhorias e funcionalidades planejadas para o projeto:

- [ ] **Controle de Acesso Baseado em Perfis (RBAC - Role-Based Access Control)**:
  - Adicionar permissões/perfis (ex: `ROLE_USER`, `ROLE_ADMIN`) na entidade `User` e proteger rotas específicas com `@PreAuthorize`.
- [ ] **Mecânica de Refresh Tokens**:
  - Permitir a renovação automatizada do token de acesso expirado sem necessidade de novo login manual.
- [ ] **Revogação e Blacklist de Tokens (Logout)**:
  - Implementar suporte a revogação de tokens armazenando tokens invalidados em memória cache (ex: Redis).
- [ ] **Externalização de Variáveis de Ambiente**:
  - Mover o segredo do JWT (`secret`) e parâmetros do banco de dados para variáveis de ambiente via arquivo `.env` ou `application.yml`.
- [ ] **Documentação Interativa com Swagger / OpenAPI 3**:
  - Integrar a biblioteca `springdoc-openapi` para geração de interface Swagger UI nos endpoints `/swagger-ui.html`.
- [ ] **Dockerização da Aplicação**:
  - Criar `Dockerfile` e `docker-compose.yml` para subir a aplicação e o banco MySQL com um único comando (`docker compose up`).
- [ ] **Cobertura de Testes Automatizados**:
  - Desenvolver testes unitários e de integração utilizando **JUnit 5**, **Mockito** e **MockMvc**.

---

<p align="center">Desenvolvido com ☕ e 💙 por Lucas Almeida.</p>
