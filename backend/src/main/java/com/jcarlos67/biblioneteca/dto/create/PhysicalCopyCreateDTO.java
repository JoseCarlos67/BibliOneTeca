package com.jcarlos67.biblioneteca.dto.create;

import com.jcarlos67.biblioneteca.dto.request.EditionRequestDTO;

public record PhysicalCopyCreateDTO(
        EditionRequestDTO edition
) {
}
