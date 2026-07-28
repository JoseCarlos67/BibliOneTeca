package com.jcarlos67.biblioneteca.model.loans;

import com.jcarlos67.biblioneteca.model.collection.PhysicalCopy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "loans")
@Getter
@Setter
public class Loan implements Serializable {
  private static final long serialVersionUID = 1L;


  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @org.hibernate.annotations.JdbcTypeCode(SqlTypes.VARCHAR)
  @Setter(AccessLevel.NONE)
  private UUID id;

  private LocalDate dateLoan;
  private LocalDate dueDate;
  private LocalDate dateReturn;
  private Integer renewal;
  private Librarian librarian;
  private Client client;
  private PhysicalCopy book;

  public Loan() {
  }

  public Loan(LocalDate dateLoan, LocalDate dueDate, LocalDate dateReturn, Integer renewal, Librarian librarian, Client client, PhysicalCopy book) {
    id = null;
    this.dateLoan = dateLoan;
    this.dueDate = dueDate;
    this.dateReturn = dateReturn;
    this.renewal = renewal;
    this.librarian = librarian;
    this.client = client;
    this.book = book;
  }

  public boolean isOverdue() {
    return true;
  }

  public void renewalLoan() {

  }
}
