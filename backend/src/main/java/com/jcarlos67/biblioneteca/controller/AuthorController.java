package com.jcarlos67.biblioneteca.controller;

import com.jcarlos67.biblioneteca.dto.AuthorRequestDTO;
import com.jcarlos67.biblioneteca.dto.AuthorResponseDTO;
import com.jcarlos67.biblioneteca.dto.AuthorUpdateRequestDTO;
import com.jcarlos67.biblioneteca.model.collection.Author;
import com.jcarlos67.biblioneteca.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/authors")
@Tag(name = "Authors", description = "Endpoints for author management and visualization")
public class AuthorController {

  @Autowired
  private AuthorService service;

  @GetMapping
  public ResponseEntity<List<AuthorResponseDTO>> find(
          @RequestParam(value = "name", required = false) String name,
          @RequestParam(value = "nationality", required = false) String nationality
          ) {

    List<Author> authors = service.find(name, nationality);
    List<AuthorResponseDTO> authorResponseDTO = authors.stream().map(author -> new AuthorResponseDTO(author.getName(), author.getDateOfBirth(), author.getDateOfDeath(), author.getNationality())).collect(Collectors.toList());

    return ResponseEntity.ok(authorResponseDTO);
  }

//  @Operation(summary = "Search for all registered authors")
//  @GetMapping
//  public ResponseEntity<List<Author>> findAll() {
//    List<Author> authorList = service.findAll();
//    return ResponseEntity.ok().body(authorList);
//  }

  @Operation(summary = "Search for author by ID", description = "Returns a specific author based on the provided ID")
  @GetMapping("{id}")
  public ResponseEntity<AuthorResponseDTO> findById(@PathVariable("id") UUID id) {
    return service.findById(id)
            .map(author -> new AuthorResponseDTO(
                    author.getName(),
                    author.getDateOfBirth(),
                    author.getDateOfDeath(),
                    author.getNationality()
            ))
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<AuthorResponseDTO> createAuthor(@Valid @RequestBody AuthorRequestDTO newAuthor) {
    AuthorResponseDTO authorResponseDTO = service.createAuthor(newAuthor);

    return ResponseEntity.status(HttpStatus.CREATED).body(authorResponseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateAuthor(@PathVariable UUID id, @Valid @RequestBody AuthorUpdateRequestDTO authorUpdateDTO) {
    Optional<Author> authorOptional = service.findById(id);

    if(authorOptional.isEmpty()){
      return ResponseEntity.notFound().build();
    }

    service.updateAuthor(id, authorUpdateDTO);
    return ResponseEntity.noContent().build();

  }

}
