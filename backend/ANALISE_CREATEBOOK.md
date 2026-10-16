# Análise da trajetória do `createBook`

Documento de apoio para correções manuais. Baseado na leitura de:

- `BookController.java`
- `BookService.java`
- `BookRequestDTO.java` / `BookResponseDTO.java`
- `Book.java`, `Edition.java`, `PhysicalCopy.java`, `Publisher.java`, `Genre.java`, `Author.java`

> **Observação:** os diagramas `docs/DB.png` e `docs/UML.png` não puderam ser lidos
> diretamente. Os pontos marcados com **[confirmar no diagrama]** precisam ser
> validados por você antes de aplicar a correção.

---

## 1. Fluxo atual do `createBook`

```
BookController.createBook(dto)
  └─> BookService.create(dto)
        ├─ new Book(); setTitle()
        ├─ resolveAuthors(dto, book)   -> busca autores por ID, saveAll, addAuthor
        ├─ resolveGenres(dto, book)    -> busca gêneros por ID, addGenre
        ├─ resolveEdition(dto, book)   -> cria Edition, liga Publisher, addEdition
        ├─ bookRepository.save(newBook)
        ├─ editionRepository.save(newEdition)   <-- redundante (cascade)
        ├─ createPhysicalCopies(qtd, edition)
        ├─ newEdition.getPhysicalCopies().addAll(...)
        ├─ physicalCopyRepository.saveAll(...)
        └─ return new BookResponseDTO(...)
```

---

## 2. Problemas identificados

### 2.1 `BookService.create` — ordem de persistência incorreta

```java
bookRepository.save(newBook);
editionRepository.save(newEdition);
```

`Book.editions` tem `cascade = CascadeType.ALL`, e `resolveEdition` já faz
`book.getEditions().add(edition)`. Portanto salvar o `Book` já cascateia a
`Edition`. O `editionRepository.save(newEdition)` é redundante e pode gerar
comportamento inconsistente (duas operações de persistência para o mesmo
agregado).

**Correção sugerida:** salvar apenas o `Book` e deixar o cascade cuidar da
`Edition` e das `PhysicalCopy` (que também têm `cascade = ALL` em `Edition`).

---

### 2.2 `PhysicalCopy` — conflito entre `@GeneratedValue` e `id` manual

```java
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;
...
public PhysicalCopy(Edition edition) {
    this.id = UUID.randomUUID();   // <-- conflito
    ...
    this.generateAssetCode();      // <-- usa o id antes do save
}
```

O `id` é setado manualmente **e** a entidade está anotada com
`@GeneratedValue`. O Hibernate pode sobrescrever o valor no `save`, o que
invalida o `assetCode` gerado a partir dele.

**Correção sugerida (escolher uma):**

- **(A)** Remover `@GeneratedValue` e gerar o `id` na aplicação (consistente
  com o uso atual do `assetCode`).
- **(B)** Manter `@GeneratedValue` e gerar o `assetCode` em um
  `@PrePersist`, quando o `id` já estiver definido pelo Hibernate.

**[confirmar no diagrama]** qual estratégia o modelo prevê para o `id` de
`PhysicalCopy`.

---

### 2.3 `PhysicalCopy.generateAssetCode` — dependência frágil

```java
Genre[] genres = edition.getBook().getGenreSet().stream()
        .limit(2)
        .toArray(Genre[]::new);
```

Depende de:

1. `edition.getBook()` não ser `null`;
2. `book.getGenreSet()` já estar populado;
3. existir pelo menos 1 gênero (senão o prefixo fica vazio).

No fluxo atual funciona porque `resolveGenres` roda antes de
`resolveEdition`, mas é frágil. Se `genreSet` estiver vazio, o `assetCode`
fica só com o sufixo do UUID, possivelmente violando a regra de negócio.

**Correção sugerida:** validar explicitamente que existe ao menos 1 gênero
antes de gerar o `assetCode`, ou lançar exceção de negócio.

---

### 2.4 `createPhysicalCopies` — sem validação de quantidade

```java
public Integer initialPhysicalCopies
```

No `BookRequestDTO` só há `@NotNull`. Se vier `0` ou negativo, nenhuma cópia
é criada silenciosamente.

**Correção sugerida:** adicionar `@Min(1)` no DTO (ou `@Positive`), conforme
regra de negócio. **[confirmar no diagrama]** se é obrigatório ter ao menos
1 cópia física.

---

### 2.5 `resolveAuthors` — `saveAll` desnecessário

```java
authorRepository.saveAll(authors);
authors.forEach(book::addAuthor);
```

Os autores já existem (foram buscados por ID). Salvar de novo é redundante e
pode disparar `UPDATE` indesejado. O `book::addAuthor` já cuida do
relacionamento.

**Correção sugerida:** remover o `saveAll`.

---

### 2.6 `resolveGenres` — inconsistente com `resolveAuthors`

`resolveGenres` **não** chama `saveAll`, enquanto `resolveAuthors` chama.
Nenhum dos dois deveria salvar. Padronizar removendo o `saveAll` de
`resolveAuthors` (ver 2.5).

---

### 2.7 `updateBook` — não atualiza a `Edition`

O `BookRequestDTO` traz uma `EditionRequestDTO` completa, mas `updateBook`
só atualiza título, autores e gêneros. A edição é ignorada silenciosamente.

**Correção sugerida:** decidir o contrato:

- **(A)** Atualizar também a `Edition` (isbn, número, ano, idioma, páginas,
  capa, sinopse, publisher).
- **(B)** Remover `edition` do `BookRequestDTO` de update (criar um DTO
  específico de update).

**[confirmar no diagrama / regra de negócio]** qual é o comportamento
esperado.

---

### 2.8 `BookResponseDTO` no `create` — `size()` pode divergir do banco

```java
newEdition.getPhysicalCopies().size()
```

As cópias são adicionadas em memória **depois** do `save`. Se o cascade não
persistir corretamente, o retorno diverge do banco.

**Correção sugerida:** garantir que o cascade de `Edition.physicalCopies`
esteja ativo (já está: `cascade = ALL, orphanRemoval = true`) e salvar apenas
o `Book`, deixando o cascade propagar.

---

## 3. Problemas no `BookController`

### 3.1 `findById` retorna `List<BookResponseDTO>`

Semanticamente estranho para busca por ID. Normalmente retornaria um único
recurso (`BookResponseDTO`) ou `404`.

**Correção sugerida:** retornar `ResponseEntity<BookResponseDTO>` ou
`ResponseEntity<List<BookResponseDTO>>` **apenas se** o contrato previr
múltiplas edições por livro. **[confirmar no diagrama]**.

---

### 3.2 `updateBook` — nome errado e redundância

```java
@PutMapping("/{id}")
public ResponseEntity<Void> updateAuthor(@PathVariable UUID id, @Validated @RequestBody BookRequestDTO dto) {
    Optional<Book> bookOptional = service.findById(id);
    if(bookOptional.isEmpty())
        return ResponseEntity.notFound().build();
    service.updateBook(id, dto);
    return ResponseEntity.noContent().build();
}
```

Problemas:

- Nome `updateBook` deveria ser `updateBook`.
- Falta `@Operation` (padrão dos outros endpoints).
- O `Optional` é redundante: `service.updateBook` já lança
  `EntityNotFoundException` se não existir.

**Correção sugerida:** renomear para `updateBook`, adicionar `@Operation`,
remover a checagem manual de existência.

---

### 3.3 `edition.getPublisher().getTradeName()` — risco de NPE

Em `findAll` e `findById`, se houver edição sem publisher no banco, isso
lança `NullPointerException`.

**Correção sugerida:** tratar `publisher` nulo no mapeamento (retornar
`null` ou string vazia), ou garantir no banco que `publisher` é `NOT NULL`.
**[confirmar no diagrama]** se `Edition.publisher` é obrigatório.

---

### 3.4 `edition.getPhysicalCopies().size()` — risco de NPE

Se a coleção não estiver inicializada (não é o caso atual, pois é
`new HashSet<>()`), quebraria. Baixo risco, mas vale padronizar.

---

## 4. Checklist de correções

- [ ] 2.1 Salvar apenas o `Book` em `create` (remover `editionRepository.save`).
- [ ] 2.2 Resolver conflito `@GeneratedValue` vs `id` manual em `PhysicalCopy`.
- [ ] 2.3 Validar gêneros antes de gerar `assetCode`.
- [ ] 2.4 Adicionar `@Min(1)` em `initialPhysicalCopies`.
- [ ] 2.5 Remover `authorRepository.saveAll` em `resolveAuthors`.
- [ ] 2.6 Padronizar `resolveGenres` / `resolveAuthors`.
- [ ] 2.7 Definir contrato de `updateBook` (atualiza Edition ou não).
- [ ] 2.8 Garantir cascade de `PhysicalCopy` via `Book`.
- [ ] 3.1 Revisar retorno de `findById`.
- [ ] 3.2 Renomear `updateBook` -> `updateBook` e limpar redundância.
- [ ] 3.3 Tratar `publisher` nulo no mapeamento.
- [ ] 3.4 Padronizar acesso a `physicalCopies`.

---

## 5. Pontos a confirmar nos diagramas

1. `PhysicalCopy.id` é gerado pelo banco ou pela aplicação?
2. `Edition.publisher` é obrigatório (`NOT NULL`)?
3. `Book` pode existir sem nenhuma `Edition`?
4. `initialPhysicalCopies` tem mínimo de 1?
5. `updateBook` deve atualizar a `Edition` também?
6. `findById` deve retornar lista (uma entrada por edição) ou objeto único?
