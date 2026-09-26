package com.jcarlos67.biblioneteca.dto.response;

import com.jcarlos67.biblioneteca.model.collection.Author;
import com.jcarlos67.biblioneteca.model.collection.Genre;
import com.jcarlos67.biblioneteca.model.collection.Publisher;

import java.util.List;

public record BookResponseDTO(
        String title,
        List<String> genres,
        List<String> authors,
        String isbn,
        Integer editionNumber,
        Integer yeatPublication,
        String language,
        Integer pageNumber,
        String urlCover,
        String synopsis,
        String publisher,
        Integer quantity
) {
}
