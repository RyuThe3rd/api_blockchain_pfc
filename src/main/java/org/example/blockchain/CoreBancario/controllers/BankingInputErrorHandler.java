package org.example.blockchain.CoreBancario.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

/** Apenas os .CoreBancario.controllers novos: não altera o tratamento das rotas anteriores. */
@RestControllerAdvice(assignableTypes = {
    BankingClientController.class,
    BankingAccountController.class,
    BankingTransactionController.class,
    StatementIssuanceController.class
})
public class BankingInputErrorHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> invalid(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }
}
