package com.jcarlos67.biblioneteca.dto.response;

import com.jcarlos67.biblioneteca.model.collection.Author;
import com.jcarlos67.biblioneteca.model.collection.Genre;

import java.util.List;

public record BookResponseDTO(
        String title,
        List<Genre> genres,
        List<Author> authors
) {
}
