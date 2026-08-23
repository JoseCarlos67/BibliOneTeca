package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.dto.create.BookCreateDTO;
import com.jcarlos67.biblioneteca.dto.request.BookRequestDTO;
import com.jcarlos67.biblioneteca.model.collection.Author;
import com.jcarlos67.biblioneteca.model.collection.Book;
import com.jcarlos67.biblioneteca.model.collection.Genre;
import com.jcarlos67.biblioneteca.repository.AuthorRepository;
import com.jcarlos67.biblioneteca.repository.BookRepository;
import com.jcarlos67.biblioneteca.repository.GenreRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

  final private BookRepository bookRepository;

  final private AuthorRepository authorRepository;

  final private GenreRepository genreRepository;
  public BookService (BookRepository bookRepository, AuthorRepository authorRepository,
                      GenreRepository genreRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.genreRepository = genreRepository;
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Optional<Book> findById(UUID id) {
    Optional<Book> book = bookRepository.findById(id);
    return book;
  }

  public Book insert(Book book) {
    return bookRepository.save(book);
  }

  public void delete(UUID id) {
    try {
      bookRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
  }

  public Book create(BookCreateDTO dto) {
    Book newBook = new Book();
    newBook.setTitle(dto.book().title());

    resolveAuthors(dto, newBook);
    resolveGenres(dto, newBook);
    return bookRepository.save(newBook);
  }

  private void resolveAuthors(BookCreateDTO dto, Book book) {
    Set<Author> authors = dto.book().authors().stream()
            .map(authorDto -> {
              if (authorDto.id() != null) {
                return authorRepository.findById(authorDto.id())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Autor não encontrado: " + authorDto.id()));
              }
              Author newAuthor = new Author();
              newAuthor.setName(authorDto.name());
              newAuthor.setDateOfBirth(authorDto.dateOfBirth());
              newAuthor.setDateOfDeath(authorDto.dateOfDeath());
              newAuthor.setNationality(authorDto.nationality());
              return authorRepository.save(newAuthor);
            }).collect(Collectors.toSet());

    authors.forEach(book::addAuthor);
  }

  private void resolveGenres(BookCreateDTO dto, Book book) {
    if (dto.book().genreIds() == null) {
      return;
    }

    dto.book().genreIds().stream()
            .map(genreId -> genreRepository.findById(genreId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Gênero não encontrado: " + genreId)))
            .forEach(book.getGenreSet()::add);
  }

}
