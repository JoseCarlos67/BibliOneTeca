package com.jcarlos67.biblioneteca.controller;

import com.jcarlos67.biblioneteca.model.loans.Librarian;
import com.jcarlos67.biblioneteca.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "librarians")
public class LibrarianController {

  @Autowired
  private LibrarianService service;

  @GetMapping
  public ResponseEntity<List<Librarian>> findAll() {
    List<Librarian> LibrarianList = service.findAll();
    return ResponseEntity.ok().body(LibrarianList);
  }

}
