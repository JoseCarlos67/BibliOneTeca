package com.jcarlos67.biblioneteca.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table (name = "books")
@Getter
@Setter
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Setter(AccessLevel.NONE)
  private UUID id;

  private String name;

  public Book() {
  }

  public Book(String name) {
    this.id = null;
    this.name = name;
  }
}
