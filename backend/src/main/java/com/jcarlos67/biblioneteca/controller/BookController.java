package com.jcarlos67.biblioneteca.controller;

import com.jcarlos67.biblioneteca.model.Book;
import com.jcarlos67.biblioneteca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookController {
  @Autowired
  BookService service;

  @GetMapping
  public ResponseEntity<List<Book>> findAll() {
    List<Book> bookList = service.findAll();
    return ResponseEntity.ok().body(bookList);
  }
}
