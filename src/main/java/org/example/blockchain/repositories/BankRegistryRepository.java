package org.example.blockchain.repositories;

import org.example.blockchain.models.BankRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRegistryRepository extends JpaRepository<BankRegistry, String> {
}