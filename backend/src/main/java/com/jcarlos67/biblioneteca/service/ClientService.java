package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.model.loans.Client;
import com.jcarlos67.biblioneteca.model.loans.Person;
import com.jcarlos67.biblioneteca.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {
  @Autowired
  private ClientRepository repository;

  public List<Client> findAll() {
    return  repository.findAll();
  }

  public Client findById(UUID id) {
    Optional<Client> client = repository.findById(id);
    return client.get();
  }

  public Client insert(Client client) {
    return repository.save(client);
  }

  public void delete(UUID id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
  }
}
