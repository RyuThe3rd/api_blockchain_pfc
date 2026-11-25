package org.example.blockchain.CoreBancario.statements.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;

/**
 * Gera o QR a partir dos bytes originais do envelope CBOR, e NUNCA do texto Base64.
 * ISO-8859-1 preserva a correspondencia 1:1 entre cada byte e o seu caracter.
 */
@Service
public class QrCodeService {
    public static final int MAX_QR_BYTES_NIVEL_M = 2331;

    /** Valida com o mesmo codificador usado na geração do PDF. */
    public void validateCapacity(byte[] envelope) {
        encodeEnvelope(envelope);
    }

    public BitMatrix encodeEnvelope(byte[] envelope) {
        if (envelope == null || envelope.length == 0) {
            throw new IllegalArgumentException("Envelope CBOR obrigatorio para gerar o QR Code");
        }
        if (envelope.length > MAX_QR_BYTES_NIVEL_M) {
            throw new QrCapacityExceededException(envelope.length, null);
        }
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.ISO_8859_1.name());
        hints.put(EncodeHintType.MARGIN, 4); // Quiet zone indispensavel para leitura.
        try {
            return new QRCodeWriter().encode(
                new String(envelope, StandardCharsets.ISO_8859_1),
                BarcodeFormat.QR_CODE, 0, 0, hints);
        } catch (WriterException e) {
            throw new QrCapacityExceededException(envelope.length, e);
        }
    }
}
