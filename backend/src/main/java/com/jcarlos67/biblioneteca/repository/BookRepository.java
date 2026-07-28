package com.jcarlos67.biblioneteca.repository;

import com.jcarlos67.biblioneteca.model.collection.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
