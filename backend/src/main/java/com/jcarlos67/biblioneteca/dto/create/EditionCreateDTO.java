package com.jcarlos67.biblioneteca.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EditionCreateDTO(
        @NotBlank String isbn,
        @NotBlank Integer editionNumber,
        @NotBlank Integer yearPublication,
        @NotBlank String language,
        @NotBlank Integer pageNumber,
        String urlCover,
        @NotBlank String synopsis,
        @NotNull UUID publisherId,

        @NotNull
        @Min(value = 1, message = "Must create at least one physical copy")
        Integer initialPhysicalCopies
        ) {
}
