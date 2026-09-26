# 🏗️ Arquitetura — API Biblioneteca

> **Stack:** Java 21 · Spring Boot 4 · Spring Data JPA · Oracle DB · Flyway · Lombok · OpenAPI (Swagger)

Este documento descreve **como a aplicação está funcionando atualmente**, separado por endpoints, mostrando como **Controller, Service, DTO, Entidade e Repository** se relacionam.

---

## 📌 Índice
1. [Visão geral da arquitetura em camadas](#-visão-geral-da-arquitetura-em-camadas)
2. [Modelo de Dados (Entidades)](#-modelo-de-dados-entidades)
3. [Mapeamento de endpoints](#-mapeamento-de-endpoints)
4. [Detalhamento por endpoint](#-detalhamento-por-endpoint)
   - [Book](#-book)
   - [Author](#-author)
   - [Edition](#-edition)
   - [Physical Copy](#-physical-copy)
   - [Publisher](#-publisher)
   - [Client](#-client)
   - [Librarian](#-librarian)
5. [Fluxo do cadastro aninhado (Ex.: Physical Copy)](#-fluxo-do-cadastro-aninhado)

---

## 🧭 Visão geral da arquitetura em camadas

```mermaid
graph TD
    subgraph "CAMADA WEB (Controllers)"
        BC[BookController]
        AC[AuthorController]
        EC[EditionController]
        PC[PysicalCopyController]
        LC[LibrarianController]
        CC[ClientController]
        PUB[PublisherController]
    end

    subgraph "CAMADA DE SERVIÇO (Services)"
        BS[BookService]
        AS[AuthorService]
        ES[EditionService]
        PS[PhysicalCopyService]
        LS[LibrarianService]
        CS[ClientService]
        PUBS[PublisherService]
    end

    subgraph "CAMADA DE ACESSO A DADOS (Repositories)"
        BR[BookRepository]
        AR[AuthorRepository]
        ER[EditionRepository]
        PR[PhysicalCopyRepository]
        GR[GenreRepository]
        LIR[LibrarianRepository]
        CLR[ClientRepository]
        PUR[PublisherRepository]
    end

    BC --> BS
    AC --> AS
    EC --> ES
    PC --> PS
    LC --> LS
    CC --> CS
    PUB --> PUBS

    BS --> BR
    BS --> AR
    AS --> AR
    ES --> ER
    PS --> PR
    PS --> ER
    PS --> BR
    PS --> AR
    PS --> PUR
    LS --> LIR
    CS --> CLR
    PUBS --> PUR
```

**Regra geral:** `Controller` (recebe requisição) → `Service` (lógica de negócio) → `Repository` (persistência) → `DB`.

---

## 🗃️ Modelo de Dados (Entidades)

```mermaid
erDiagram
    BOOK ||--o{ EDITION : "tem"
    BOOK }o--o{ AUTHOR : "author_book"
    BOOK }o--o{ GENRE : "book_genre"
    PUBLISHER ||--o{ EDITION : "publica"
    EDITION ||--o{ PHYSICAL_COPY : "possui"
    PHYSICAL_COPY ||--o{ LOAN : "emprestada"
    CLIENT ||--o{ LOAN : "faz"
    LIBRARIAN ||--o{ LOAN : "registra"

    BOOK {
        UUID id PK
        string title
    }
    EDITION {
        UUID id PK
        string isbn UK
        int year_publication
        string language
        int page_number
        string cover
        string synopsis
        UUID fk_book FK
        UUID fk_publisher FK
    }
    PHYSICAL_COPY {
        UUID id PK
        string asset_code UK
        string status
        UUID fk_edition FK
    }
    AUTHOR {
        UUID id PK
        string name
        date date_of_birth
        date date_of_death
        string nationality
    }
    GENRE {
        UUID id PK
        string name
    }
    PUBLISHER {
        UUID id PK
        string legal_name
        string trade_name
        string cnpj UK
        string site_url
    }
    CLIENT {
        UUID id PK
        string cpf UK
        string full_name
        date date_of_birth
        string email UK
        string cell_number
        date registration_expiration
    }
    LIBRARIAN {
        UUID id PK
        string password
        string full_name
        date date_of_birth
        string cpf UK
        string email UK
    }
    LOAN {
        UUID id PK
        date date_loan
        date due_date
        date date_return
        int renewal
        UUID fk_librarian FK
        UUID fk_client FK
        UUID fk_physical_copy FK
    }
```

> **Relacionamentos N-M:** `BOOK ⇄ AUTHOR` (via `author_book`), `BOOK ⇄ GENRE` (via `book_genre`). São gerenciados pela entidade `Book` (owner da relação).

---

## 🗺️ Mapeamento de endpoints

| HTTP | Path | Controller | Service | Resposta |
|------|------|-----------|---------|----------|
| `GET` | `/books` | BookController | BookService | `List<BookResponseDTO>` |
| `GET` | `/books/{id}` | BookController | BookService | `BookResponseDTO` ou 404 |
| `POST` | `/books` | BookController | BookService | `BookResponseDTO` (201) |
| `GET` | `/authors` | AuthorController | AuthorService | `List<Author>` |
| `GET` | `/authors/{id}` | AuthorController | AuthorService | `AuthorResponseDTO` ou 404 |
| `GET` | `/editions` | EditionController | EditionService | `List<EditionDTO>` |
| `DELETE` | `/editions/{id}` | EditionController | EditionService | 204 / 404 |
| `POST` | `/physical-copies` | PysicalCopyController | PhysicalCopyService | `PhysicalCopyResponseDTO` (201) |
| `GET` | `/clients` | ClientController | ClientService | `List<Client>` |
| `GET` | `/librarians` | LibrarianController | LibrarianService | `List<Librarian>`, `List<Person>` |
| `GET` | `/publishers` | PublisherController | PublisherService | `List<Publisher>` |

---

## 🔎 Detalhamento por endpoint

### 📖 Book

#### `GET /books` — Listar todos os livros

```mermaid
sequenceDiagram
    participant C as BookController
    participant S as BookService
    participant R as BookRepository
    C->>S: findAll()
    S->>R: findAll()
    R-->>S: List<Book>
    S-->>C: List<Book>
    Note over C: Mapeia cada Book → BookResponseDTO<br/>(title, genres, authors)
```

**DTOs envolvidos:**
- `BookResponseDTO(String title, List<Genre> genres, List<Author> authors)` — resposta

---

#### `GET /books/{id}` — Buscar livro por ID

```mermaid
sequenceDiagram
    participant C as BookController
    participant S as BookService
    participant R as BookRepository
    C->>S: findById(id)
    S->>R: findById(id)
    R-->>S: Optional<Book>
    S-->>C: Optional<Book>
    alt Book presente
        C-->>C: mapeia para BookResponseDTO
        C-->>Client: 200 OK
    else Não encontrado
        C-->>Client: 404 NOT_FOUND
    end
```

---

#### `POST /books` — Criar livro

```mermaid
sequenceDiagram
    participant Cl as Client
    participant C as BookController
    participant S as BookService
    participant AR as AuthorRepository
    participant BR as BookRepository
    Cl->>C: POST /books (BookCreateDTO)
    C->>S: create(BookCreateDTO)
    Note over S,C: BookCreateDTO envolve BookRequestDTO<br/>(title, genreIds, authors)
    S->>S: resolveAuthors()<br/>autor por id OU novo
    S->>AR: findById / save(Author)
    AR-->>S: Author(s)
    S->>S: resolveGenres()<br/>busca gêneros por ids
    S->>BR: save(Book)
    BR-->>S: Book persistido c/ autores e gêneros
    S-->>C: Book
    C-->>Cl: 201 CREATED + BookResponseDTO
```

**DTOs envolvidos:**
- `BookCreateDTO(BookRequestDTO book)` — entrada (wrapper)
- `BookRequestDTO(UUID id, String title, Set<UUID> genreIds, Set<AuthorRequestDTO> authors)` — entrada
- `AuthorRequestDTO(UUID id, name, dateOfBirth, dateOfDeath, nationality)` — entrada
- `BookResponseDTO(title, genres, authors)` — resposta

---

### 👤 Author

#### `GET /authors` — Listar autores
- `AuthorController.findAll()` → `service.findAll()` → `AuthorRepository.findAll()` → **retorna `List<Author>`** (≈ entidade exposta).

#### `GET /authors/{id}` — Buscar autor por ID
- `AuthorController.findById()` → `service.findById()` → `AuthorRepository.findById()` → ```Optional<Author>``` → mapeia para `AuthorResponseDTO` ou 404.

**DTO de resposta:** `AuthorResponseDTO(name, dateOfBirth, dateOfDeath, nationality)`

```mermaid
graph LR
    AC[AuthorController] --> AS[AuthorService] --> AR[AuthorRepository] --> DB[(Oracle DB)]
    AS -->|findById mapeia| RD[AuthorResponseDTO]
    AS -->|findAll expõe| EN[Author entidade]
```

---

### 📚 Edition

#### `GET /editions` — Listar edições (completa — "o livro definitivo")
- `EditionController.findAll()` → `service.findAll()` → `EditionRepository.findAll()` → mapeia cada `Edition` para `EditionDTO`.

**DTO de resposta:** `EditionDTO(title, author, publisher, isbn, edition_number, year_publication, language, genre, page_number, cover, synopsis)`

> O `EditionDTO` **agrega** dados do `Book` (title, author, genre) + `Publisher` + dados da `Edition`. Por isso é o "objeto completo" visto pelo usuário.

```mermaid
graph LR
    E[Edition] -->|getBook| B[Book]
    B -->|getTitle| BD[EditionDTO.title]
    B -->|getAuthorSet| BD2[EditionDTO.author]
    B -->|getGenreSet| BD3[EditionDTO.genre]
    E -->|getPublisher| P[Publisher]
    P -->|diretos| BD4[EditionDTO.publisher]
    E -->|isbn, year, language, etc| BD5[demais campos]
```

---

#### `DELETE /editions/{id}` — Deletar edição

```mermaid
sequenceDiagram
    participant C as EditionController
    participant S as EditionService
    participant R as EditionRepository
    C->>S: deleteById(id)
    S->>R: findById(id)
    alt Não existe
        S-->>C: throw (404 NOT_FOUND)
    else Existe
        S->>S: carrega physicalCopies (força lazy)<br/>para cascata funcionar
        S->>R: delete(edition)
        S-->>C: ok
        C-->>Client: 204 NO_CONTENT
    end
```

> ⚠️ **Regra específica:** se a edição possuir `PhysicalCopy`, o JPA precisa carregar a coleção LAZY (`getPhysicalCopies().size()`) para que o `orphanRemoval` delete as cópias antes de remover a edição.

---

### 📦 Physical Copy

#### `POST /physical-copies` — Criar cópia física (cadastro aninhado)

Este é o endpoint mais complexo: recebe um **DTO aninhado** e faz resolução/criação de **Book, Publisher e Edition** automaticamente.

```mermaid
sequenceDiagram
    participant Cl as Client
    participant C as PysicalCopyController
    participant S as PhysicalCopyService
    participant BR as BookRepository
    participant AR as AuthorRepository
    participant PR as PublisherRepository
    participant ER as EditionRepository
    participant PCR as PhysicalCopyRepository

    Cl->>C: POST /physical-copies (PhysicalCopyCreateDTO)
    C->>S: create(PhysicalCopyCreateDTO)

    Note over S: dto.edition() = EditionRequestDTO<br/>que contém book e publisher aninhados

    rect rgb(230,240,255)
        Note over S,AR: resolveBook(BookRequestDTO)
        S->>BR: findById(id) se dto.id() != null
        alt novo book
            S->>AR: cria/vincula autores inline
            S->>BR: save(Book)
        end
        BR-->>S: Book
    end

    rect rgb(240,255,240)
        Note over S,PR: resolverPublisher(PublisherRequestDTO)
        S->>PR: findByCnpj(cnpj) OU findById(id) OU save
        PR-->>S: Publisher
    end

    rect rgb(255,245,230)
        Note over S,ER: resolveEdition(EditionRequestDTO)
        S->>ER: findById OU save
        ER-->>S: Edition
    end

    S->>S: cria PhysicalCopy (status AVAILABLE)
    S->>PCR: save(PhysicalCopy)
    PCR-->>S: PhysicalCopy
    S-->>C: PhysicalCopy
    C-->>Cl: 201 CREATED + PhysicalCopyResponseDTO
```

**DTOs envolvidos (entrada aninhada):**
- `PhysicalCopyCreateDTO(EditionRequestDTO edition)`
- `EditionRequestDTO(id, isbn, editionNumber, publicationYear, language, pageNumber, urlCover, synopsis, PublisherRequestDTO publisher, BookRequestDTO book)`
- `PublisherRequestDTO(id, legalName, tradeName, cnpj, siteUrl)`
- `BookRequestDTO(id, title, genreIds, authors)`

**DTO de resposta:**
- `PhysicalCopyResponseDTO(UUID id, String assetCode, String status)`

> **Nota:** o `assetCode` é gerado no `@PrePersist` da entidade `PhysicalCopy`, derivado dos primeiros gêneros do livro + parte do `id`.

---

### 🏢 Publisher

#### `GET /publishers` — Listar editoras
- `PublisherController.findAll()` → `PublisherService.findAll()` → `PublisherRepository.findAll()` → retorna `List<Publisher>`.

O `PublisherRepository` também possui `findByCnpj(String cnpj)` usado internamente pelo `PhysicalCopyService` para evitar CNPJ duplicado.

---

### 👥 Client

#### `GET /clients` — Listar clientes
- `ClientController.findAll()` → `ClientService.findAll()` → `ClientRepository.findAll()` → retorna `List<Client>`.

> ⚠️ **Pendência:** CRUD incompleto — apenas leitura. As **RN03** (unicidade CPF/e-mail) e **RN04** (validade de 3 meses) ainda não têm endpoints de criação/validação no controller.

---

### 🔑 Librarian

#### `GET /librarians` — Listar bibliotecários
- `LibrarianController.findAll()` → `LibrarianService.findAll()` → `LibrarianRepository.findAll()` → retorna `List<Librarian>` e também `List<Person>`.

> ⚠️ `Librarian` estende `Person`. A listagem atual expõe os dois tipos.

---

## 🧩 Fluxo do cadastro aninhado (resumo)

O padrão usado em `POST /physical-copies` demonstra uma **composição de DTOs aninhados**, onde um único payload de entrada aciona a criação em cascata de várias entidades:

```mermaid
graph TD
    PC[PhysicalCopyCreateDTO] --> ER[EditionRequestDTO]
    ER --> P[PublisherRequestDTO]
    ER --> B[BookRequestDTO]
    B --> AU[AuthorRequestDTO x N]
    B --> G[genreIds x N]

    P --> Ent1[Publisher entity]
    B --> Ent2[Book entity]
    AU --> Ent3[Author entity]
    G --> Ent4[Genre entity]
    ER --> Ent5[Edition entity]
    Ent5 --> Ent6[PhysicalCopy entity (status AVAILABLE)]
```

---

## 📁 Estrutura dos pacotes

```
com.jcarlos67.biblioneteca
├── controller/      → endpoints (Book, Author, Edition, PysicalCopy, Publisher, Client, Librarian)
├── service/         → lógica de negócio
├── repository/      → interfaces Spring Data JPA
├── model/
│   ├── collection/  → Book, Edition, Author, Genre, Publisher, PhysicalCopy (+ enums)
│   └── loans/       → Client, Librarian, Person, Loan
├── dto/
│   ├── create/      → BookCreateDTO, PhysicalCopyCreateDTO
│   ├── request/     → BookRequestDTO, AuthorRequestDTO, EditionRequestDTO, PublisherRequestDTO
│   └── response/    → BookResponseDTO, AuthorResponseDTO, PhysicalCopyResponseDTO
├── config/          → SecurityConfig, DevConfig, OpenApiConfig
└── BiblionetecaApplication   → entry point
```

---

## 🚧 Estado atual & pendências

| Módulo | Endpoints CRUD completos? | Observações |
|--------|:---:|-------------|
| Book | Parcial | GET listar/id, POST feito |
| Author | Parcial | GET listar/id |
| Edition | Parcial | GET listar, DELETE |
| PhysicalCopy | Parcial | Somente POST (criação) |
| Publisher | Parcial | Somente GET listar |
| Client | Incompleto | Somente GET listar; sem RN03/RN04 |
| Librarian | Incompleto | Somente GET listar; sem auth |
| Loan | **Ausente** | Entidade existe, mas sem controller/repository/endpoints |
| Genre | **Ausente** | Repository existe, mas sem service/controller |

---

*Documento gerado a partir da análise do código-fonte atual do backend.*
