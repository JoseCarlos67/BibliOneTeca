package com.jcarlos67.biblioneteca.dto;

import java.util.UUID;

public record PublisherRequestDTO(
        UUID id,
        String legalName,
        String tradeName,
        String cnpj,
        String siteUrl
) {
}
