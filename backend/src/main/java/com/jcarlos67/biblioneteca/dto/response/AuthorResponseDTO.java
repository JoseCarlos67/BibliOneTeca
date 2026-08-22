package com.jcarlos67.biblioneteca.dto.response;

import java.time.LocalDate;

public record AuthorResponseDTO (
        String name,
        LocalDate dateOfBirth,
        LocalDate dateOfDeath,
        String nationality
) {

}
