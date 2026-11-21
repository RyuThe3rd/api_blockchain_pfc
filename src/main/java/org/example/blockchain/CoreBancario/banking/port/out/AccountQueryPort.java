package org.example.blockchain.CoreBancario.banking.port.out;

import org.example.blockchain.CoreBancario.banking.model.BankAccount;

/** Consulta de contas: não expõe comandos de alteração. */
public interface AccountQueryPort {
    BankAccount findByNumber(String accountNo);
    BankAccount findById(long accountId);
}
