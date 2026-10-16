package com.jcarlos67.biblioneteca.dto;

import java.util.List;

public record BookResponseDTO(
        String title,
        List<String> genres,
        List<String> authors,
        String isbn,
        Integer editionNumber,
        Integer yearPublication,
        String language,
        Integer pageNumber,
        String urlCover,
        String synopsis,
        String publisher,
        Integer quantity
) {
}
