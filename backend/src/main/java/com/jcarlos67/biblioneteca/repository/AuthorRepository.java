package com.jcarlos67.biblioneteca.repository;

import com.jcarlos67.biblioneteca.model.collection.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
