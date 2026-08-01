package com.jcarlos67.biblioneteca.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AuthorRequestDTO {
  private UUID id;
  private String name;
  private LocalDate dateOfBirth;
  private LocalDate dateOfDeath;
  private String nationality;

  public AuthorRequestDTO(UUID id, String name, LocalDate dateOfBirth, LocalDate dateOfDeath, String nationality) {
    this.id = id;
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.dateOfDeath = dateOfDeath;
    this.nationality = nationality;
  }
}
