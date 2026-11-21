package org.example.blockchain.CoreBancario.banking.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Formato não negativo suportado pelo protocolo CBOR v1. */
public record MoneyParts(long units, int cents) {
    public static MoneyParts from(BigDecimal value) {
        if (value == null) throw new IllegalArgumentException("O montante é obrigatório");
        if (value.signum() < 0) {
            throw new IllegalArgumentException("O protocolo CBOR v1 ainda não representa saldos negativos");
        }
        try {
            BigDecimal decimal = value.setScale(2, RoundingMode.UNNECESSARY);
            long centsTotal = decimal.movePointRight(2).longValueExact();
            return new MoneyParts(centsTotal / 100, (int) (centsTotal % 100));
        } catch (ArithmeticException ex) {
            throw new IllegalArgumentException("Montante fora da precisão suportada (2 casas decimais)", ex);
        }
    }
}
