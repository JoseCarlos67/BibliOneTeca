package com.jcarlos67.biblioneteca.model.loans;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "librarians")
public class Librarian extends Person {
  private String password;

  public Librarian() {
  }

  public Librarian(String cpf, String fullName, LocalDate dateOfBirth, String email, String password) {
    super(cpf, fullName, dateOfBirth, email);
    this.password = password;
  }
}
