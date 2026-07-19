package com.jcarlos67.biblioneteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.SqlTypes;

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
  @org.hibernate.annotations.JdbcTypeCode(SqlTypes.VARCHAR)
  @Setter(AccessLevel.NONE)
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

  @ManyToOne
  @JoinColumn(name = "fk_book")
  private Book book;

  @ManyToOne
  @JoinColumn(name = "fk_publisher")
  private Publisher publisher;

  public Edition(){
  }

  public Edition(String isbn, Integer edition_number, int year_publication, String language, int page_number, String cover, String synopsis, Book book, Publisher publisher) {
    this.id = null;
    this.isbn = isbn;
    this.edition_number = edition_number;
    this.year_publication = year_publication;
    this.language = language;
    this.page_number = page_number;
    this.cover = cover;
    this.synopsis = synopsis;
    this.book = book;
    this.publisher = publisher;
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
