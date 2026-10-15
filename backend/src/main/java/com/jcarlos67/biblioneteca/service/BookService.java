package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.dto.EditionDTO;
import com.jcarlos67.biblioneteca.dto.create.EditionRequestDTO;
import com.jcarlos67.biblioneteca.dto.request.BookRequestDTO;
import com.jcarlos67.biblioneteca.dto.request.BookUpdateRequestDTO;
import com.jcarlos67.biblioneteca.dto.response.BookResponseDTO;
import com.jcarlos67.biblioneteca.model.collection.*;
import com.jcarlos67.biblioneteca.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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

  // Implementar
  public Optional<Book> find(String name, String isbn, int year_publication, String language, int page_number, String Publisher) {
    return null;
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

  public BookResponseDTO create(BookRequestDTO dto) {
    Book newBook = new Book();
    newBook.setTitle(dto.title());

    resolveAuthors(dto, newBook);

    resolveGenres(dto, newBook);

    Edition newEdition = resolveEdition(dto, newBook);

    bookRepository.save(newBook);
    editionRepository.save(newEdition);

    return new BookResponseDTO(newBook.getTitle(), newBook.getGenreSet().stream().map(Genre::getName).toList(), newBook.getAuthorSet().stream().map(Author::getName).toList(), newEdition.getIsbn(), newEdition.getEditionNumber(), newEdition.getYear_publication(), newEdition.getLanguage(), newEdition.getPage_number(), newEdition.getCover(), newEdition.getSynopsis(), newEdition.getPublisher().getTradeName(), newEdition.getPhysicalCopies().size());
  }

  public void updateBook(UUID id, BookRequestDTO dto) {
    Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));

    book.setTitle(dto.title());
    resolveAuthors(dto, book);
    resolveGenres(dto, book);

    bookRepository.save(book);
  }

  private void resolveAuthors(BookRequestDTO dto, Book book) {
    Set<UUID> authorIds = dto.authorId();

    List<Author> authors = authorRepository.findAllById(dto.authorId());

    if (authors.size() != authorIds.size()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more of the specified authors were not found in the system");
    }
    authorRepository.saveAll(authors);
    authors.forEach(book::addAuthor);
  }

  private void resolveGenres(BookRequestDTO dto, Book book) {
    Set<UUID> genreIds = dto.genreId();

    List<Genre> genres = genreRepository.findAllById(genreIds);

    if (genres.size() != genreIds.size()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more of the specified genres were not found in the system");
    }

    genres.forEach(book::addGenre);
  }

  private Edition resolveEdition(BookRequestDTO dto, Book book) {
    Edition edition = new Edition();
    edition.setIsbn(dto.edition().getIsbn());
    edition.setEditionNumber(dto.edition().getEdition_number());
    edition.setYear_publication(dto.edition().getYear_publication());
    edition.setLanguage(dto.edition().getLanguage());
    edition.setPage_number(dto.edition().getPage_number());
    edition.setCover(dto.edition().getCover());
    edition.setSynopsis(dto.edition().getSynopsis());
    edition.setBook(book);

    Publisher publisher = publisherRepository.findById(dto.edition().getPublisher().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found in the system"));

    edition.setPublisher(publisher);
    edition.setBook(book);
    book.getEditions().add(edition);
    return edition;
  }
}
