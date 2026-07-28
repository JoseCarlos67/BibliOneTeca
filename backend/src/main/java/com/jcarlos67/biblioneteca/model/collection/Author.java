package com.jcarlos67.biblioneteca.model.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table (name = "authors")
@Getter
@Setter
public class Author implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @org.hibernate.annotations.JdbcTypeCode(SqlTypes.VARCHAR)
  @Setter(AccessLevel.NONE)
  private UUID id;


  private String name;
  private LocalDate dateOfBirth;
  private LocalDate dateOfDeath;

  @NotNull(message = "The nationality cannot be null!")
  private String nationality;

  @JsonIgnore
  @ManyToMany(mappedBy = "authorSet")
  private Set<Book> bookSet = new HashSet<>();

  public Author() {
  }

  public Author(String name, LocalDate dateOfBirth, LocalDate dateOfDeath, String nationality) {
    this.id =null;
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.dateOfDeath = dateOfDeath;
    this.nationality = nationality;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Author author = (Author) o;
    return Objects.equals(getId(), author.getId()) && Objects.equals(getName(), author.getName()) && Objects.equals(getDateOfBirth(), author.getDateOfBirth()) && Objects.equals(getDateOfDeath(), author.getDateOfDeath()) && Objects.equals(getNationality(), author.getNationality());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getDateOfBirth(), getDateOfDeath(), getNationality());
  }
}
