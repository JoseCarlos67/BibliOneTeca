package com.jcarlos67.biblioneteca.dto;

import com.jcarlos67.biblioneteca.model.Author;
import com.jcarlos67.biblioneteca.model.Edition;
import com.jcarlos67.biblioneteca.model.Genre;
import com.jcarlos67.biblioneteca.model.Publisher;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class EditionDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private String title;
  private Set<Author> author = new HashSet<>();
  private Publisher publisher;
  private String isbn;
  private Integer edition_number;
  private int year_publication;
  private String language;
  private Set<Genre> genre = new HashSet<>();
  private int page_number;
  private String cover;
  private String synopsis;

  public EditionDTO() {
  }

  public EditionDTO(Edition edition) {
    this.title = edition.getBook().getTitle();
    this.author = edition.getBook().getAuthorSet();
    this.publisher = edition.getPublisher();
    this.isbn = edition.getIsbn();
    this.edition_number = edition.getEdition_number();
    this.year_publication = edition.getYear_publication();
    this.language = edition.getLanguage();
    this.genre = edition.getBook().getGenreSet();
    this.page_number = edition.getPage_number();
    this.cover = edition.getCover();
    this.synopsis = edition.getSynopsis();
  }
}
