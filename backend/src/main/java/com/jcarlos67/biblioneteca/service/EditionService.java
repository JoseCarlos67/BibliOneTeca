package com.jcarlos67.biblioneteca.service;

import com.jcarlos67.biblioneteca.dto.request.EditionRequestDTO;
import com.jcarlos67.biblioneteca.model.collection.Edition;
import com.jcarlos67.biblioneteca.model.collection.Publisher;
import com.jcarlos67.biblioneteca.repository.EditionRepository;
import com.jcarlos67.biblioneteca.repository.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EditionService {

  final private EditionRepository editionRepository;
  final private PublisherRepository publisherRepository;

  public EditionService(EditionRepository editionRepository, PublisherRepository publisherRepository) {
    this.editionRepository = editionRepository;
    this.publisherRepository = publisherRepository;
  }

  public List<Edition> findAll() {
    return editionRepository.findAll();
  }

  public Edition findByIsbn(String isbn) {
    Optional<Edition> edition = editionRepository.findByIsbn(isbn);
    return edition.get();
  }

  public Optional<Edition> findById(UUID id) {
    return editionRepository.findById(id);
  }

  public Edition insert(Edition edition) {
    return editionRepository.save(edition);
  }

  @Transactional
  public void deleteById(UUID id) {
    Optional<Edition> editionOpt = editionRepository.findById(id);
    if (editionOpt.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
              "Edition not found");
    }

    Edition edition = editionOpt.get();
    edition.getPhysicalCopies().size();
    editionRepository.delete(edition);
  }

  @Transactional
  public void updateEdition(UUID id, EditionRequestDTO dto) {
    Edition edition = editionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Edition not found"));

    edition.setIsbn(dto.isbn());
    edition.setEditionNumber(dto.editionNumber());
    edition.setYear_publication(dto.yearPublication());
    edition.setLanguage(dto.language());
    edition.setPage_number(dto.pageNumber());
    edition.setCover(dto.urlCover());
    edition.setSynopsis(dto.synopsis());

    Publisher publisher = publisherRepository.findById(dto.publisherId())
            .orElseThrow(() -> new EntityNotFoundException("Publisher not found"));
    edition.setPublisher(publisher);

    editionRepository.save(edition);
  }

}
