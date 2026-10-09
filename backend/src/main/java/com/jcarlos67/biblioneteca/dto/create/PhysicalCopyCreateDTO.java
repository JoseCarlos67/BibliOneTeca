package com.jcarlos67.biblioneteca.dto.create;

import com.jcarlos67.biblioneteca.dto.request.EditionRequestDTO;
import jakarta.validation.constraints.NotNull;

public record PhysicalCopyCreateDTO(
        @NotNull(message = "The edition of the book is mandatory")
        EditionRequestDTO edition
) {
}
