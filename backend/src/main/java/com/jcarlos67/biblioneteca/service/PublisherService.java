package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.model.Publisher;
import com.jcarlos67.biblioneteca.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PublisherService {
  @Autowired
  private PublisherRepository repository;

  public List<Publisher> findAll() {
    return repository.findAll();
  }

  public Publisher findById(UUID id) {
    Optional<Publisher> Publisher = repository.findById(id);
    return Publisher.get();
  }

  public Publisher insert(Publisher Publisher) {
    return repository.save(Publisher);
  }

  public void delete(UUID id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
  }

  // test method
  public void deleteAll() {
    repository.deleteAll();
  }
}
