package com.jcarlos67.biblioneteca.dto.response;

import java.util.UUID;

public record  PhysicalCopyResponseDTO (
        UUID id,
        String assetCode,
        String status
) {
}
