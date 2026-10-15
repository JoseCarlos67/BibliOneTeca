package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.dto.create.PhysicalCopyCreateDTO;
import com.jcarlos67.biblioneteca.dto.request.BookRequestDTO;
import com.jcarlos67.biblioneteca.dto.request.EditionRequestDTO;
import com.jcarlos67.biblioneteca.dto.request.PublisherRequestDTO;
import com.jcarlos67.biblioneteca.model.collection.*;
import com.jcarlos67.biblioneteca.model.collection.enums.PhysicalCopyStatus;
import com.jcarlos67.biblioneteca.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
