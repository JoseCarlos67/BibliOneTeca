package com.jcarlos67.biblioneteca.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

public record EditionRequestDTO(
        @NotBlank(message = "The book's ISBN is mandatory")
        @Size(min = 10, max = 12, message = "The book's ISBN must be between {min} and {max} characters long")
        String isbn,

        @NotNull(message = "The book's edition is mandatory")
        @Min(value = 1, message = "The book edition must be at least equal to {value}")
        @Positive(message = "The edition number must be a positive value")
        Integer editionNumber,

        @NotNull(message = "The year of publication is mandatory")
        Integer yearPublication,

        @NotBlank(message = "The language of the book is mandatory")
        @Size(min = 3, message = "The book's language must have at least {min} letters")
        String language,

        @NotNull(message = "The number of pages in the book is mandatory")
        @Min(value = 49, message = "The minimum number of pages must be at least {value}")
        @Positive(message = "The page number must be a positive value")
        Integer pageNumber,

        @URL(message = "Invalid URL")
        String urlCover,

        @NotBlank(message = "The book synopsis is mandatory")
        @Size(min = 100, max = 5000, message = "The synopsis must be between {min} and {max} characters long")
        String synopsis,

        @NotNull(message = "The book needs a publisher")
        UUID publisherId
) {
}
