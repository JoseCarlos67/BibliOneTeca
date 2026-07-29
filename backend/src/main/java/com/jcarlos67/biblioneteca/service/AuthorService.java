package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.model.collection.Author;
import com.jcarlos67.biblioneteca.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {
  @Autowired
  private AuthorRepository repository;

  public List<Author> findAll() {
    return repository.findAll();
  }

  public Author findById(UUID id) {
    Optional<Author> author = repository.findById(id);
    return author.get();
  }

  public void delete(UUID id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
  }

  public void deleteAll() {
    repository.deleteAll();
  }
}
