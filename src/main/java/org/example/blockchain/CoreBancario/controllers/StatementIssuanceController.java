package org.example.blockchain.CoreBancario.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.blockchain.CoreBancario.banking.dto.IssueStatementRequest;
import org.example.blockchain.CoreBancario.statements.StatementIssuanceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Base64;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statements")
public class StatementIssuanceController {
    private final StatementIssuanceService statements;

    /** Compatibilidade: agora inclui pdfBase64 e pdfFileName, sem remover campos anteriores. */
    @PostMapping("/issue")
    public Map<String, Object> issue(@Valid @RequestBody IssueStatementRequest request) throws Exception {
        return statements.issue(request);
    }

    /** Alternativa para clientes que pretendam receber directamente application/pdf. */
    @PostMapping(value = "/issue/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> issuePdf(@Valid @RequestBody IssueStatementRequest request) throws Exception {
        Map<String, Object> result = statements.issue(request);
        byte[] pdf = Base64.getDecoder().decode((String) result.get("pdfBase64"));
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + result.get("pdfFileName") + "\"")
            .contentLength(pdf.length)
            .body(pdf);
    }
}
