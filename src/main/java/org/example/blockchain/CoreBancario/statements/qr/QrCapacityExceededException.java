package org.example.blockchain.CoreBancario.statements.qr;

/** Erro de capacidade do envelope final, antes da ancoragem. */
public class QrCapacityExceededException extends IllegalArgumentException {
    public QrCapacityExceededException(int envelopeBytes, Throwable cause) {
        super("O envelope CBOR de " + envelopeBytes
                + " bytes nao cabe num unico QR Code de nivel M (limite teorico em modo Byte: "
                + QrCodeService.MAX_QR_BYTES_NIVEL_M
                + " bytes). Reduza o volume de dados ou o periodo do extracto.",
                cause);
    }
}
