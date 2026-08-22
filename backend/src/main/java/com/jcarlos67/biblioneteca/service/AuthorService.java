package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.model.collection.Author;
import com.jcarlos67.biblioneteca.repository.AuthorRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

  final private AuthorRepository repository;

  public AuthorService(AuthorRepository repository) {
    this.repository = repository;
  }

  public List<Author> findAll() {
    return repository.findAll();
  }

  public Optional<Author> findById(UUID id) {
    Optional<Author> author = repository.findById(id);
    return author;
  }

  public void delete(UUID id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
  }

}
