package com.jcarlos67.biblioneteca.model.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table (name = "books")
@Getter
@Setter
public class Book implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @org.hibernate.annotations.JdbcTypeCode(SqlTypes.VARCHAR)
  @Setter(AccessLevel.NONE)
  private UUID id;

  private String title;

  @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
  private Set<Edition> editions = new HashSet<>();

  @JsonIgnore
  @ManyToMany()
  @JoinTable(
          name = "author_book",
          joinColumns = @JoinColumn(name = "fk_book"),
          inverseJoinColumns = @JoinColumn(name = "fk_author")
  )
  private Set<Author> authorSet = new HashSet<>();

  @JsonIgnore
  @ManyToMany()
  @JoinTable(
          name = "book_genre",
          joinColumns = @JoinColumn(name = "fk_book"),
          inverseJoinColumns = @JoinColumn(name = "fk_genre")
  )
  private Set<Genre> genreSet = new HashSet<>();

  public Book() {
  }

  public Book(String title) {
    this.id = null;
    this.title = title;
  }

  public void addAuthor(Author author) {
    this.authorSet.add(author);
    author.getBookSet().add(this);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return Objects.equals(getId(), book.getId()) && Objects.equals(getTitle(), book.getTitle());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getTitle());
  }
}
