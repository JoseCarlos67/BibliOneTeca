package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.dto.create.AuthorCreateDTO;
import com.jcarlos67.biblioneteca.dto.response.AuthorResponseDTO;
import com.jcarlos67.biblioneteca.dto.update.AuthorUpdateDTO;
import com.jcarlos67.biblioneteca.model.collection.Author;
import com.jcarlos67.biblioneteca.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
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
    if (!repository.existsById(id))
      throw new EntityNotFoundException("Author not found!");

    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
  }

  public AuthorResponseDTO updateAuthor(UUID id, AuthorUpdateDTO updateDTO) {
    Author author = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));

    author.setName(updateDTO.name());
    author.setDateOfBirth(updateDTO.dateOfBirth());
    author.setDateOfDeath(updateDTO.dateOfDeath());
    author.setNationality(updateDTO.nationality());

    author = repository.save(author);

    return new AuthorResponseDTO(
            author.getName(),
            author.getDateOfBirth(),
            author.getDateOfDeath(),
            author.getNationality()
    );
  }

  private void resolveAuthor(AuthorCreateDTO authorDTO, Author authorEntity) {
    authorEntity.setName(authorDTO.name());
    authorEntity.setDateOfBirth(authorDTO.dateOfBirth());
    authorEntity.setDateOfDeath(authorDTO.dateOfDeath());
    authorEntity.setNationality(authorDTO.nationality());
  }

}
