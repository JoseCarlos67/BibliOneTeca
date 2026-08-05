package com.jcarlos67.biblioneteca.dto.request;

import java.util.Set;
import java.util.UUID;

public record BookRequestDTO(
        UUID id,
        String title,
        Set<UUID> genreIds,
        Set<AuthorRequestDTO> authors
) {
}
