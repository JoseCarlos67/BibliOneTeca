package com.jcarlos67.biblioneteca.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record BookCreateDTO(
        @NotBlank(message = "The book's name is required")
        String title,

        @NotNull(message = "The book's genre is required")
        Set<UUID> genreIds,

        @NotNull(message = "The book's author is required")
        Set<UUID> authors,

        @NotNull(message = "The book's genre is required")
        EditionCreateDTO edition
) {
}
