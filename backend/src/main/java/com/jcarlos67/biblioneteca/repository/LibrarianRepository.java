package com.jcarlos67.biblioneteca.repository;

import com.jcarlos67.biblioneteca.model.loans.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LibrarianRepository extends JpaRepository<Librarian, UUID> {
}
