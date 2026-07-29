package com.jcarlos67.biblioneteca.repository;

import com.jcarlos67.biblioneteca.model.loans.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
