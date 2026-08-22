package com.jcarlos67.biblioneteca.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorRequestDTO(
        UUID id,
        String name,
        LocalDate dateOfBirth,
        LocalDate dateOfDeath,
        String nationality
) {}

