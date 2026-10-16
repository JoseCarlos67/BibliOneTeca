package com.jcarlos67.biblioneteca.dto;

import com.jcarlos67.biblioneteca.model.collection.Edition;

import java.util.List;
import java.util.UUID;

public record EditionResponseDTO(
        UUID id,
        String title,
        List<String> authors,
        String publisher,
        String isbn,
        Integer editionNumber,
        Integer yearPublication,
        String language,
        List<String> genres,
        Integer pageNumber,
        String urlCover,
        String synopsis
) {

  public EditionResponseDTO(Edition edition) {
    this(
            edition.getId(),
            edition.getBook().getTitle(),
            edition.getBook().getAuthorSet().stream().map(author -> author.getName()).toList(),
            edition.getPublisher().getTradeName(),
            edition.getIsbn(),
            edition.getEditionNumber(),
            edition.getYear_publication(),
            edition.getLanguage(),
            edition.getBook().getGenreSet().stream().map(genre -> genre.getName()).toList(),
            edition.getPage_number(),
            edition.getCover(),
            edition.getSynopsis()
    );
  }
}
