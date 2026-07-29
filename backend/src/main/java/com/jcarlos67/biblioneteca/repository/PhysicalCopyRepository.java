package com.jcarlos67.biblioneteca.repository;

import com.jcarlos67.biblioneteca.model.collection.PhysicalCopy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhysicalCopyRepository extends JpaRepository<PhysicalCopy, UUID> {
}
