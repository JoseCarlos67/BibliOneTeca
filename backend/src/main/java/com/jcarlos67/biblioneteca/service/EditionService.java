package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.model.Edition;
import com.jcarlos67.biblioneteca.repository.EditionRepository;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EditionService {
  private EditionRepository repository;

  public List<Edition> findAll() {
    return repository.findAll();
  }

  public Edition findByIdbn(String isbn) {
    Optional<Edition> edition = repository.findByIsbn(isbn);
    return edition.get();
  }

  public Edition insert(Edition edition) {
    return repository.save(edition);
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
