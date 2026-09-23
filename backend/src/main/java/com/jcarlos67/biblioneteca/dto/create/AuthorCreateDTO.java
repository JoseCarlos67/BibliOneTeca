package com.jcarlos67.biblioneteca.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record AuthorCreateDTO(
        @NotBlank(message = "The author's name is required")
        String name,

        @NotNull(message = "The author's date of birth is mandatory")
        @PastOrPresent(message = "The date of birth cannot be in the future")
        LocalDate dateOfBirth,

        @PastOrPresent(message = "The date of birth cannot be in the future")
        LocalDate dateOfDeath,

        @NotBlank (message = "The author's nationality is required")
        String nationality
) {
}
