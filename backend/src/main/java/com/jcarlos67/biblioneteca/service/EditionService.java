package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.model.collection.Edition;
import com.jcarlos67.biblioneteca.repository.EditionRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EditionService {

  final private EditionRepository repository;

  public EditionService(EditionRepository repository) {
    this.repository = repository;
  }

  public List<Edition> findAll() {
    return repository.findAll();
  }

  public Edition findByIsbn(String isbn) {
    Optional<Edition> edition = repository.findByIsbn(isbn);
    return edition.get();
  }

  public Optional findById(UUID id) {
    return repository.findById(id);
  }

  public Edition insert(Edition edition) {
    return repository.save(edition);
  }

  @Transactional
  public void delete(Edition edition) {
    repository.delete(edition);
  }

}
