package com.jcarlos67.biblioneteca.dto.create;

import jakarta.validation.constraints.*;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

public record EditionCreateDTO(
        @NotBlank(message = "The books's ISBN is mandatory")
        @Size(min = 10, max = 12, message = "The book's ISBN must be between {min} and {max} characters lon")
        String isbn,

        @NotNull(message = "The books's edition is mandatory")
        @Min(value = 1, message = "The book edition must be at least equal to {value}")
        @Positive(message = "The edition number must be a positivo value")
        Integer editionNumber,

        @NotNull(message = "The year of publication is mandatory")
//        @PastOrPresent(message = "The publication date cannot be in the future")
//        Trocar o tipo do valor para Year. Verificar no banco também.
        Integer yearPublication,

        @NotBlank(message = "The language of the book is mandatory")
        @Size(min = 3,message = "The author's language must have at least {min} letters")
        String language,

        @NotNull(message = "The number of pages in the book is mandatory")
        @Min(value = 49, message = "The minimum number of pages must be at least {value}")
        @Positive(message = "The page number must be a positive value")
        Integer pageNumber,

        @URL(message = "Invalid URL")
        String urlCover,

        @NotBlank(message = "The book synopsis is mandatory")
        @Size(min = 100, max = 5000, message = "The synopsis must be between {min} and {max} character long")
        String synopsis,

        @NotNull(message = "The book needs a publisher")
        UUID publisherId,

        @NotNull(message = "The number of copies is mandatory")
        @Min(value = 1, message = "The book must contain at least {value} copy(ies)")
        @Positive(message = "The number of copies must be a positive value")
        Integer initialPhysicalCopies
        ) {
}
