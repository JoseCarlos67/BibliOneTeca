package com.jcarlos67.biblioneteca.controller;

import com.jcarlos67.biblioneteca.model.loans.Client;
import com.jcarlos67.biblioneteca.model.loans.Person;
import com.jcarlos67.biblioneteca.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "clients")
public class ClientController {
  @Autowired
  ClientService service;

  @GetMapping
  public ResponseEntity<List<Client>> findAll() {
    List<Client> clientList = service.findAll();
    return ResponseEntity.ok().body(clientList);
  }
}
