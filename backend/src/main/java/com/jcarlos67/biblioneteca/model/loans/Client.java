package com.jcarlos67.biblioneteca.model.loans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client extends Person {
  @Column(nullable = false)
  private String cellNumber;
  @Column(nullable = false)
  private LocalDate registrationExpiration;

  public Client() {
  }

  public Client(String cpf, String fullName, LocalDate dateOfBirth, String email, String cellNumber, LocalDate registrationExpiration) {
    super(cpf, fullName, dateOfBirth, email);
    this.cellNumber = cellNumber;
    this.registrationExpiration = registrationExpiration;
  }
}
