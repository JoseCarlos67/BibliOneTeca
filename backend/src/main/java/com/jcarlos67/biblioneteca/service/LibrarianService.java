package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.model.loans.Librarian;
import com.jcarlos67.biblioneteca.repository.LibrarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LibrarianService {
  @Autowired
  private LibrarianRepository repository;

  public List<Librarian> findAll() {
    return  repository.findAll();
  }

  public Librarian findById(UUID id) {
    Optional<Librarian> Librarian = repository.findById(id);
    return Librarian.get();
  }

  public Librarian insert(Librarian Librarian) {
    return repository.save(Librarian);
  }

  public void delete(UUID id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
  }
}
