package com.jcarlos67.biblioneteca.dto.request;

import com.jcarlos67.biblioneteca.dto.EditionDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record BookUpdateRequestDTO(
        @NotBlank(message = "The book's name is required")
        String title,

        @NotEmpty(message = "The book's genre is required")
        Set<@NotNull UUID> genreId,

        @NotEmpty(message = "The book's author is required")
        Set<@NotNull UUID> authorId
) {
}
