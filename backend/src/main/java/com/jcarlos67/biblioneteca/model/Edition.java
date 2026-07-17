package com.jcarlos67.biblioneteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table (name = "editions")
@Getter
@Setter
public class Edition implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  @Column(nullable = false, unique = true)
  private String isbn;

  private Integer edition_number;

  @Column(nullable = false)
  private int year_publication;

  @Column(nullable = false)
  private String language;

  @Column(nullable = false)
  private int page_number;

  @Column(nullable = false)
  private String cover;

  @Column(nullable = false)
  @Lob
  private String synopsis;

  public Edition(){
  }

  public Edition(String isbn, Integer edition_number, int year_publication, String language, int page_number, String cover, String synopsis) {
    this.id = null;
    this.isbn = isbn;
    this.edition_number = edition_number;
    this.year_publication = year_publication;
    this.language = language;
    this.page_number = page_number;
    this.cover = cover;
    this.synopsis = synopsis;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Edition edition = (Edition) o;
    return Objects.equals(getId(), edition.getId()) && Objects.equals(getIsbn(), edition.getIsbn());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getIsbn());
  }
}
