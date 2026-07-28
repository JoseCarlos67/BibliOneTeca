package com.jcarlos67.biblioneteca.model.loans;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class Person implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @org.hibernate.annotations.JdbcTypeCode(SqlTypes.VARCHAR)
  @Setter(AccessLevel.NONE)
  private UUID id;

  @Column(nullable = false, unique = true, length = 11)
  private String cpf;
  @Column(nullable = false)
  private String fullName;
  @Column(nullable = false)
  private LocalDate dateOfBirth;

  private String email;

  public Person() {
  }

  public Person(String cpf, String fullName, LocalDate dateOfBirth, String email) {
    this.cpf = cpf;
    this.fullName = fullName;
    this.dateOfBirth = dateOfBirth;
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(getId(), person.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }
}
