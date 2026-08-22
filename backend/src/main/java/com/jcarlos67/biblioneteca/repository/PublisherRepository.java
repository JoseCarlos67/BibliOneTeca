package com.jcarlos67.biblioneteca.repository;

import com.jcarlos67.biblioneteca.model.collection.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PublisherRepository extends JpaRepository<Publisher, UUID> {
  Optional<Publisher> findByCnpj(String cnpj);
}
