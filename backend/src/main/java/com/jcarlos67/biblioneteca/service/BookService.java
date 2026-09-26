package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.dto.create.BookCreateDTO;
import com.jcarlos67.biblioneteca.model.collection.*;
import com.jcarlos67.biblioneteca.repository.*;
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

  final private EditionRepository editionRepository;

  final private PublisherRepository publisherRepository;

  public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, EditionRepository editionRepository, PublisherRepository publisherRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.genreRepository = genreRepository;
    this.editionRepository = editionRepository;
    this.publisherRepository = publisherRepository;
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
    newBook.setTitle(dto.title());

    resolveAuthors(dto, newBook);

    resolveGenres(dto, newBook);

    resolveEdition(dto, newBook);

    return bookRepository.save(newBook);
  }

  private void resolveAuthors(BookCreateDTO dto, Book book) {
    Set<UUID> authorIds = dto.authors();

    List<Author> authors = authorRepository.findAllById(dto.authors());

    if (authors.size() != authorIds.size()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more of the specified authors were not found in the system");
    }
    authorRepository.saveAll(authors);
    authors.forEach(book::addAuthor);
  }

  private void resolveGenres(BookCreateDTO dto, Book book) {
    Set<UUID> genreIds = dto.genreIds();

    List<Genre> genres = genreRepository.findAllById(genreIds);

    if (genres.size() != genreIds.size()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more of the specified genres were not found in the system");
    }

    genres.forEach(book::addGenre);
  }

  private void resolveEdition(BookCreateDTO dto, Book book) {
    Edition edition = new Edition();
    edition.setIsbn(dto.edition().isbn());
    edition.setEditionNumber(dto.edition().editionNumber());
    edition.setYear_publication(dto.edition().yearPublication());
    edition.setLanguage(dto.edition().language());
    edition.setPage_number(dto.edition().pageNumber());
    edition.setCover(dto.edition().urlCover());
    edition.setSynopsis(dto.edition().synopsis());
    edition.setBook(book);

    Publisher publisher = publisherRepository.findById(dto.edition().publisherId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found in the system"));

    edition.setPublisher(publisher);
    edition.setBook(book);
    book.getEditions().add(edition);
  }
}
