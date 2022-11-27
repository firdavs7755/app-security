package com.example.appsecurity.repositories;

import com.example.appsecurity.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "contact")
public interface ContactRepository extends JpaRepository<Contact, UUID> {
}
