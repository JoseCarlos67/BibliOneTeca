package com.jcarlos67.biblioneteca.repository;

import com.jcarlos67.biblioneteca.model.collection.Edition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EditionRepository extends JpaRepository<Edition, UUID> {
  Optional<Edition> findByIsbn(String isbn);
}
