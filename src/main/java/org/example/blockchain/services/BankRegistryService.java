package org.example.blockchain.services;

import org.example.blockchain.models.BankRegistry;
import org.example.blockchain.repositories.BankRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankRegistryService {

    @Autowired
    private BankRegistryRepository repository;

    public BankRegistry findByBankId(String bankId) {
        return repository.findById(bankId).orElse(null);
    }
}