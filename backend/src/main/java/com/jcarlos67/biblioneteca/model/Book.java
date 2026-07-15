package com.jcarlos67.biblioneteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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
  @Setter(AccessLevel.NONE)
  private UUID id;

  @NotNull(message = "The name cannot be null!")
  @Column(nullable = false)
  private String name;

  @NotNull(message = "The author(s) cannot be null!")
  @Column(nullable = false)
  @ManyToMany()
  @JoinTable(
          name = "author_book",
          joinColumns = @JoinColumn(name = "fk_book"),
          inverseJoinColumns = @JoinColumn(name = "fk_author")
  )
  private Set<Author> authorSet = new HashSet<>();

  public Book() {
  }

  public Book(String name) {
    this.id = null;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return Objects.equals(getId(), book.getId()) && Objects.equals(getName(), book.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName());
  }
}
