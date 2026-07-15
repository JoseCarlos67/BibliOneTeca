package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.model.Book;
import com.jcarlos67.biblioneteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
  @Autowired
  private BookRepository repository;

  public List<Book> findAll() {
    return repository.findAll();
  }

  public Book findById(UUID id) {
    Optional<Book> book = repository.findById(id);
    return book.get();
  }

  public Book insert(Book book) {
    return repository.save(book);
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
