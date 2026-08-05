package com.jcarlos67.biblioneteca.dto.request;

import java.util.UUID;

public record EditionRequestDTO(
        UUID id,
        String isbn,
        Integer editionNumber,
        Integer publicationYear,
        String language,
        Integer pageNumber,
        String urlCover,
        String synopsis,
        PublisherRequestDTO publisher,
        BookRequestDTO book
) {
}
