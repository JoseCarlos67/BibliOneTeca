package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.dto.BookRequestDTO;
import com.jcarlos67.biblioneteca.dto.EditionRequestDTO;
import com.jcarlos67.biblioneteca.dto.PhysicalCopyCreateDTO;
import com.jcarlos67.biblioneteca.dto.PublisherRequestDTO;
import com.jcarlos67.biblioneteca.model.collection.*;
import com.jcarlos67.biblioneteca.model.collection.enums.PhysicalCopyStatus;
import com.jcarlos67.biblioneteca.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhysicalCopyService {

  private final PhysicalCopyRepository physicalCopyRepository;
  private final EditionRepository editionRepository;
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final PublisherRepository publisherRepository;

  public PhysicalCopyService(
          @Autowired PhysicalCopyRepository physicalCopyRepository,
          @Autowired EditionRepository editionRepository,
          @Autowired BookRepository bookRepository,
          @Autowired AuthorRepository authorRepository,
          @Autowired PublisherRepository publisherRepository
  ) {
    this.physicalCopyRepository = physicalCopyRepository;
    this.editionRepository = editionRepository;
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.publisherRepository = publisherRepository;
  }

  @Transactional
  public PhysicalCopy create(PhysicalCopyCreateDTO dto) {
    EditionRequestDTO  editionDto = dto.edition();
    Book book = resolveBook(editionDto.book());
    Publisher publisher = resolverPublisher(editionDto.publisher());
    Edition edition = resolveEdition(editionDto, book, publisher);

    PhysicalCopy copy = new PhysicalCopy();
    copy.setEdition(edition);
    copy.setStatus(PhysicalCopyStatus.AVAILABLE);

    return physicalCopyRepository.save(copy);
  }

  private Book resolveBook(BookRequestDTO dto) {
    if (dto.id() != null) {
      return bookRepository.findById(dto.id()).orElseThrow();
    }

    Book newBook = new Book();
    newBook.setTitle(dto.title());

    Set<Author> authors = dto.authors().stream()
            .map(authorDto -> {
              if (authorDto.id() != null) {
                return authorRepository.findById(authorDto.id()).orElseThrow();
              }
              Author newAuthor = new Author();
              newAuthor.setName(authorDto.name());
              newAuthor.setDateOfBirth(authorDto.dateOfBirth());
              newAuthor.setDateOfDeath(authorDto.dateOfDeath());
              newAuthor.setNationality(authorDto.nationality());
              return authorRepository.save(newAuthor);
            }).collect(Collectors.toSet());

    authors.forEach(newBook::addAuthor);

    return bookRepository.save(newBook);
  }

  private Publisher resolverPublisher(PublisherRequestDTO dto) {
    if (dto.id() != null){
      return publisherRepository.findById(dto.id()).orElseThrow();
    }
    Publisher newPublisher = new Publisher();
    newPublisher.setLegalName(dto.legalName());
    return publisherRepository.save(newPublisher);
  }


  private Edition resolveEdition(EditionRequestDTO dto, Book book, Publisher publisher) {
    if (dto.id() != null) {
      return editionRepository.findById(dto.id()).orElseThrow();
    }
    Edition newEdition = new Edition();
    newEdition.setIsbn(dto.isbn());
    newEdition.setEditionNumber(dto.editionNumber());
    newEdition.setBook(book);
    newEdition.setPublisher(publisher);
    return editionRepository.save(newEdition);
  }
}
