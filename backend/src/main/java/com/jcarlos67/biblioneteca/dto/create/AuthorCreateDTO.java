package com.jcarlos67.biblioneteca.dto.create;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AuthorCreateDTO(
        @NotBlank(message = "The author's name is required")
        String name,

        @NotBlank(message = "The author's date of birth is mandatory")
        LocalDate dateOfBirth,
        LocalDate dateOfDeath,
        String nationality
) {
}
