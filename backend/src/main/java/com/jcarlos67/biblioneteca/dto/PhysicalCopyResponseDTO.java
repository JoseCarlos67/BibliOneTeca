package com.jcarlos67.biblioneteca.dto;

import java.util.UUID;

public record  PhysicalCopyResponseDTO (
        UUID id,
        String assetCode,
        String status
) {
}
