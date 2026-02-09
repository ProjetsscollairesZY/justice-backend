package com.Justice.Justice.Repositories;

import com.Justice.Justice.Models.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
    List<Client> findByTypeClient(String typeClient);
     List<Client> findByAvocatId(String avocatId);
    Optional<Client> findByEmail(String email);
}