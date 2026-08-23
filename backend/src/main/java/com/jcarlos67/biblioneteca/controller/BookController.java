package com.jcarlos67.biblioneteca.controller;

import com.jcarlos67.biblioneteca.dto.create.BookCreateDTO;
import com.jcarlos67.biblioneteca.dto.response.BookResponseDTO;
import com.jcarlos67.biblioneteca.model.collection.Book;
import com.jcarlos67.biblioneteca.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/books")
@Tag(name = "Books", description = "Endpoints for book visualization")
public class BookController {

  @Autowired
  private BookService service;

  @Operation(summary = "Search for all registered books")
  @GetMapping
  public ResponseEntity<List<BookResponseDTO>> findAll() {
    return ResponseEntity.ok(
            service.findAll()
                    .stream()
                    .map(book -> new BookResponseDTO(
                            book.getTitle(),
                            book.getGenreSet().stream().toList(),
                            book.getAuthorSet().stream().toList()
                    ))
                    .toList()
    );
  }

  @Operation(summary = "Search for book by ID", description = "Returns a specific book based on the provided ID")
  @GetMapping("{id}")
  public ResponseEntity<BookResponseDTO> findById(@PathVariable UUID id) {
    return service.findById(id)
            .map(book -> new BookResponseDTO(
                    book.getTitle(),
                    book.getGenreSet().stream().toList(),
                    book.getAuthorSet().stream().toList()
            ))
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Register a new book", description = "Creates a book with its authors and genres. Existing authors are linked by id, new authors are created inline.")
  @PostMapping
  public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookCreateDTO dto) {
    Book savedBook = service.create(dto);

    BookResponseDTO responseDTO = new BookResponseDTO(
            savedBook.getTitle(),
            savedBook.getGenreSet().stream().toList(),
            savedBook.getAuthorSet().stream().toList()
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

}
