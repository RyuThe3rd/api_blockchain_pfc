
package org.example.blockchain.codec;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

@Component
public class ZlibCodec {

    /**
     * Comprime os bytes CBOR utilizando ZLIB.
     *
     * Utiliza DEFLATE com cabeçalho ZLIB
     * e checksum Adler-32.
     *
     * Compatível com ZLibDecoder do pacote
     * archive utilizado na Flutter PWA.
     *
     * @param cborBytes Bytes CBOR originais.
     * @return Bytes comprimidos em formato ZLIB.
     * @throws IOException Se ocorrer um erro na compressão.
     */
    public byte[] compress(byte[] cborBytes) throws IOException {

        Objects.requireNonNull(
                cborBytes,
                "Os bytes CBOR não podem ser nulos"
        );

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        // false = produzir fluxo com formato ZLIB,
        // não DEFLATE raw.
        Deflater deflater = new Deflater(
                Deflater.BEST_COMPRESSION,
                false
        );

        try (DeflaterOutputStream stream =
                     new DeflaterOutputStream(output, deflater)) {

            stream.write(cborBytes);
        }

        return output.toByteArray();
    }
}
