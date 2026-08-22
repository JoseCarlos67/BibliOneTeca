package com.jcarlos67.biblioneteca.controller;

import com.jcarlos67.biblioneteca.dto.EditionDTO;
import com.jcarlos67.biblioneteca.model.collection.Edition;
import com.jcarlos67.biblioneteca.service.EditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/editions")
public class EditionController {

  @Autowired
  private EditionService service;

  @GetMapping
  public ResponseEntity<List<EditionDTO>> findAll() {
    List<Edition> editionList = service.findAll();
    List<EditionDTO> editionDTOS = editionList.stream().map(x -> new EditionDTO(x)).collect(Collectors.toList());
    return ResponseEntity.ok().body(editionDTOS);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable(name = "id") String idString) {
    UUID id = UUID.fromString(idString);
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

}
