package com.jcarlos67.biblioneteca.dto;

import java.time.LocalDate;

public record AuthorResponseDTO(
        String name,
        LocalDate dateOfBirth,
        LocalDate dateOfDeath,
        String nationality
) {
}
