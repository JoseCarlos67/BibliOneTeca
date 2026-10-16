package com.jcarlos67.biblioneteca.validator;

import com.jcarlos67.biblioneteca.exception.DuplicateRecordException;
import com.jcarlos67.biblioneteca.model.collection.Author;
import com.jcarlos67.biblioneteca.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

  private AuthorRepository repository;

  public AuthorValidator(AuthorRepository repository) {
    this.repository = repository;
  }

  public void validate(Author author) {
    if(thereIsARegisteredAuthor(author))
      throw new DuplicateRecordException("Registered author");
  }

  public boolean thereIsARegisteredAuthor(Author author) {
    Optional<Author> authorFound = repository.findByNameAndDateOfBirthAndNationality(
            author.getName(),
            author.getDateOfBirth(),
            author.getNationality()
    );

    if(author.getId() == null) {
      return authorFound.isPresent();
    }

    return !author.getId().equals(authorFound.get().getId())&& authorFound.isPresent();
  }

}
