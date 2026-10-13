package com.jcarlos67.biblioneteca.dto.create;

import com.jcarlos67.biblioneteca.model.collection.Author;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AuthorCreateDTO(
        @NotBlank(message = "The author's name is required")
        @Size(min = 3, message = "The author's name must have at least 3 letters")
        String name,

        @NotNull(message = "The author's date of birth is mandatory")
        @PastOrPresent(message = "The date of birth cannot be in the future")
        LocalDate dateOfBirth,

        @Past(message = "The date of birth cannot be in the future")
        LocalDate dateOfDeath,

        @NotBlank(message = "The author's nationality is required")
        @Size(min = 3, message = "The author's nationality must have 3 letters")
        String nationality
) {

  public Author mapToAuthor() {
    Author author = new Author();

    author.setName(this.name);
    author.setDateOfBirth(this.dateOfBirth);
    author.setDateOfDeath(this.dateOfDeath);
    author.setNationality(this.nationality);

    return author;
  }

}
