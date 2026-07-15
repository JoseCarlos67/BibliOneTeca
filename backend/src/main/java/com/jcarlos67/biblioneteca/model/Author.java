package com.jcarlos67.biblioneteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table (name = "authors")
@Getter
@Setter
public class Author implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull(message = "The name cannot be null!")
  @Column(nullable = false)
  private String name;
  @NotNull(message = "The date of birth cannot be null!")
  @Column(nullable = false)
  private LocalDate dateOfBirth;
  private LocalDate dateOfDeath;
  @NotNull(message = "The nationality cannot be null!")
  @Column(nullable = false)
  private String nationality;

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
