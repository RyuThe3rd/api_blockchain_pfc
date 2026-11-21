package org.example.blockchain.CoreBancario.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.dto.IssueStatementRequest;
import org.example.blockchain.CoreBancario.statements.StatementIssuanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statements")
public class StatementIssuanceController {
    private final StatementIssuanceService statements;

    @PostMapping("/issue")
    public Map<String, Object> issue(@Valid @RequestBody IssueStatementRequest request) throws Exception {
        return statements.issue(request);
    }
}
