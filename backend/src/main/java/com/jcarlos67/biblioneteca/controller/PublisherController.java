package com.jcarlos67.biblioneteca.controller;

import com.jcarlos67.biblioneteca.model.collection.Publisher;
import com.jcarlos67.biblioneteca.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/publishers")
public class PublisherController {

  @Autowired
  private PublisherService service;

  @GetMapping
  public ResponseEntity<List<Publisher>> findAll() {
    List<Publisher> publishersList = service.findAll();
    return ResponseEntity.ok().body(publishersList);
  }

}
