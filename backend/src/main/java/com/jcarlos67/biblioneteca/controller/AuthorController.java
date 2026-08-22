package com.jcarlos67.biblioneteca.controller;

import com.jcarlos67.biblioneteca.model.collection.Author;
import com.jcarlos67.biblioneteca.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController {

  @Autowired
  private AuthorService service;

  @GetMapping
  public ResponseEntity<List<Author>> findAll() {
    List<Author> authorList = service.findAll();
    return ResponseEntity.ok().body(authorList);
  }

}
