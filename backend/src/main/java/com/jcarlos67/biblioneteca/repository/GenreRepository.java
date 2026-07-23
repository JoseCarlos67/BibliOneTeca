package com.jcarlos67.biblioneteca.repository;

import com.jcarlos67.biblioneteca.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
}
