package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.dto.create.AuthorCreateDTO;
import com.jcarlos67.biblioneteca.dto.response.AuthorResponseDTO;
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

  public AuthorResponseDTO createAuthor(AuthorCreateDTO newAuthorDTO) {
    Author newAuthorEntity = new Author();
    resolveAuthor(newAuthorDTO, newAuthorEntity);

    repository.save(newAuthorEntity);

    return new AuthorResponseDTO(newAuthorDTO.name(), newAuthorDTO.dateOfBirth(), newAuthorDTO.dateOfDeath(), newAuthorDTO.nationality());
  }

  public void delete(UUID id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
  }

  private void resolveAuthor(AuthorCreateDTO authorDTO, Author authorEntity) {
    authorEntity.setName(authorDTO.name());
    authorEntity.setDateOfBirth(authorDTO.dateOfBirth());
    authorEntity.setDateOfDeath(authorDTO.dateOfDeath());
    authorEntity.setNationality(authorDTO.nationality());
  }

}
