package com.jcarlos67.biblioneteca.controller;

import com.jcarlos67.biblioneteca.dto.EditionRequestDTO;
import com.jcarlos67.biblioneteca.dto.EditionResponseDTO;
import com.jcarlos67.biblioneteca.model.collection.Edition;
import com.jcarlos67.biblioneteca.service.EditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/editions")
public class EditionController {

  @Autowired
  private EditionService service;

  @GetMapping
  public ResponseEntity<List<EditionResponseDTO>> findAll() {
    List<Edition> editionList = service.findAll();
    List<EditionResponseDTO> editionDTOS = editionList.stream().map(EditionResponseDTO::new).collect(Collectors.toList());
    return ResponseEntity.ok().body(editionDTOS);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable(name = "id") String idString) {
    UUID id = UUID.fromString(idString);
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateEdition(@PathVariable UUID id, @Validated @RequestBody EditionRequestDTO dto) {
    Optional<Edition> editionOptional = service.findById(id);

    if (editionOptional.isEmpty())
      return ResponseEntity.notFound().build();

    service.updateEdition(id, dto);
    return ResponseEntity.noContent().build();
  }

}
