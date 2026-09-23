package org.example.blockchain.repositories;

import org.example.blockchain.models.DocumentProof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DocumentProofRepository extends JpaRepository<DocumentProof, Long> {
    Optional<DocumentProof> findByDocumentHash(String documentHash);
}