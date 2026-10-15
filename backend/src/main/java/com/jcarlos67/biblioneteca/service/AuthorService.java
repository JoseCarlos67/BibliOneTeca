package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.dto.create.AuthorCreateDTO;
import com.jcarlos67.biblioneteca.dto.response.AuthorResponseDTO;
import com.jcarlos67.biblioneteca.dto.update.AuthorUpdateDTO;
import com.jcarlos67.biblioneteca.model.collection.Author;
import com.jcarlos67.biblioneteca.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
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

  public List<Author> find(String name, String nationality) {
    if (name != null && nationality != null)
      return repository.findByNameAndNationality(name, nationality);

    if (name != null)
      return repository.findByName(name);

    if (nationality != null)
      return repository.findByNationality(nationality);

    return repository.findAll();
  }

  public AuthorResponseDTO createAuthor(AuthorCreateDTO newAuthorDTO) {
    Author newAuthorEntity = newAuthorDTO.mapToAuthor();

    repository.save(newAuthorEntity);

    return new AuthorResponseDTO(newAuthorDTO.name(), newAuthorDTO.dateOfBirth(), newAuthorDTO.dateOfDeath(), newAuthorDTO.nationality());
  }

  public void delete(UUID id) {
    Author author = repository.findById(id)
            .orElseThrow(() ->
                    new EntityNotFoundException("Author not found")
            );

    repository.deleteById(id);
  }

  public void updateAuthor(UUID id, AuthorUpdateDTO updateDTO) {
    Author author = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));

    author.setName(updateDTO.name());
    author.setDateOfBirth(updateDTO.dateOfBirth());
    author.setDateOfDeath(updateDTO.dateOfDeath());
    author.setNationality(updateDTO.nationality());

    repository.save(author);
  }

}
