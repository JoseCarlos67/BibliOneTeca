package com.jcarlos67.biblioneteca.dto.request;

import java.util.UUID;

public record EditionRequestDTO(
        UUID id,
        String isbn,
        Integer editionNumber,
        int yearPublication,
        String language,
        Integer pageNumber,
        String urlCover,
        String synopsis,
        UUID publisherId,
        BookRequestDTO book
) {
}
