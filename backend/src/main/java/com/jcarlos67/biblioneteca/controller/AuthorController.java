package com.jcarlos67.biblioneteca.controller;

import com.jcarlos67.biblioneteca.dto.create.AuthorCreateDTO;
import com.jcarlos67.biblioneteca.dto.response.AuthorResponseDTO;
import com.jcarlos67.biblioneteca.model.collection.Author;
import com.jcarlos67.biblioneteca.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/authors")
@Tag(name = "Authors", description = "Endpoints for author management and visualization")
public class AuthorController {

  @Autowired
  private AuthorService service;

  @Operation(summary = "Search for all registered authors")
  @GetMapping
  public ResponseEntity<List<Author>> findAll() {
    List<Author> authorList = service.findAll();
    return ResponseEntity.ok().body(authorList);
  }

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
  public ResponseEntity<AuthorResponseDTO> createAuthor(@RequestBody AuthorCreateDTO newAuthor) {
    AuthorResponseDTO authorResponseDTO = service.createAuthor(newAuthor);

    return ResponseEntity.status(HttpStatus.CREATED).body(authorResponseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }


}
