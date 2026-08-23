package com.jcarlos67.biblioneteca.dto.create;

import com.jcarlos67.biblioneteca.dto.request.BookRequestDTO;

public record BookCreateDTO(
        BookRequestDTO book
) {
}
