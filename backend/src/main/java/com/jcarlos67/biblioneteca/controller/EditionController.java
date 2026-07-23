package com.jcarlos67.biblioneteca.controller;

import com.jcarlos67.biblioneteca.model.Edition;
import com.jcarlos67.biblioneteca.service.EditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/editions")
public class EditionController {
  @Autowired
  EditionService service;

  @GetMapping
  public ResponseEntity<List<Edition>> findAll() {
    List<Edition> editionList = service.findAll();
    return ResponseEntity.ok().body(editionList);
  }
}
