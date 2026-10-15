package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.repository.*;
import org.springframework.stereotype.Service;

@Service
public class PhysicalCopyService {

  private final PhysicalCopyRepository physicalCopyRepository;
  private final EditionRepository editionRepository;
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final PublisherRepository publisherRepository;

  public PhysicalCopyService(
          EditionRepository editionRepository,
          PhysicalCopyRepository physicalCopyRepository,
          BookRepository bookRepository,
          AuthorRepository authorRepository,
          PublisherRepository publisherRepository
  ) {
    this.physicalCopyRepository = physicalCopyRepository;
    this.editionRepository = editionRepository;
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.publisherRepository = publisherRepository;
  }
}
