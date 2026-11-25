package org.example.blockchain.service;

import org.example.blockchain.CoreBancario.statements.qr.QrCapacityExceededException;
import org.example.blockchain.CoreBancario.statements.qr.QrCodeService;
import org.example.blockchain.codec.CborEnvelopeEncoder;
import org.example.blockchain.codec.TypeACborEncoder;
import org.example.blockchain.codec.TypeBCborEncoder;
import org.example.blockchain.codec.ZlibCodec;
import org.example.blockchain.dto.TypeAAnchorRequestDTO;
import org.example.blockchain.dto.TypeBAnchorRequestDTO;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class QrCapacityBeforeAnchorTest {
    // Credencial exclusivamente ficticia; nenhuma chamada real a blockchain.
    private static final String TEST_KEY = "1";
    private final CryptoService crypto = mock(CryptoService.class);
    private final StatementRegistryService registry = mock(StatementRegistryService.class);
    private final CborEnvelopeEncoder envelopeEncoder = mock(CborEnvelopeEncoder.class);
    private final QrCodeService qr = new QrCodeService();

    private void prepareMocks(int length) throws Exception {
        when(crypto.signDocumentHash(anyString(), any(Credentials.class)))
                .thenReturn("0x" + "00".repeat(65));
        when(envelopeEncoder.encode(anyInt(), anyString(), anyInt(), anyString(),
                any(byte[].class), any(byte[].class))).thenReturn(new byte[length]);
    }

    private TypeAStatementService typeA() throws Exception {
        TypeACborEncoder encoder = mock(TypeACborEncoder.class);
        when(encoder.encode(any(TypeAAnchorRequestDTO.class), anyString()))
                .thenReturn(new byte[]{1});
        return new TypeAStatementService(encoder, envelopeEncoder, crypto, registry, qr);
    }

    private TypeBStatementService typeB() throws Exception {
        TypeBCborEncoder encoder = mock(TypeBCborEncoder.class);
        ZlibCodec zlib = mock(ZlibCodec.class);
        when(encoder.encode(any(TypeBAnchorRequestDTO.class), anyString()))
                .thenReturn(new byte[]{1});
        when(zlib.compress(any(byte[].class))).thenReturn(new byte[]{2});
        return new TypeBStatementService(encoder, zlib, envelopeEncoder, crypto, registry, qr);
    }

    private TypeAAnchorRequestDTO requestA() {
        TypeAAnchorRequestDTO request = new TypeAAnchorRequestDTO();
        request.setPrivateKey(TEST_KEY);
        return request;
    }

    private TypeBAnchorRequestDTO requestB() {
        TypeBAnchorRequestDTO request = new TypeBAnchorRequestDTO();
        request.setPrivateKey(TEST_KEY);
        return request;
    }

    @Test
    void oversizedTypeAIsNotAnchored() throws Exception {
        prepareMocks(2332);
        TypeAStatementService service = typeA();
        assertThrows(QrCapacityExceededException.class, () -> service.anchor(requestA()));
        verifyNoInteractions(registry);
    }

    @Test
    void oversizedTypeBIsNotAnchored() throws Exception {
        prepareMocks(2332);
        TypeBStatementService service = typeB();
        assertThrows(QrCapacityExceededException.class, () -> service.anchor(requestB()));
        verifyNoInteractions(registry);
    }

    @Test
    void validTypeAAnchorsOnce() throws Exception {
        prepareMocks(200);
        when(registry.anchorStatement(anyString(), any(Credentials.class))).thenReturn("test-tx");
        assertEquals(200, typeA().anchor(requestA()).get("qrByteLength"));
        verify(registry, times(1)).anchorStatement(anyString(), any(Credentials.class));
    }

    @Test
    void validTypeBAnchorsOnce() throws Exception {
        prepareMocks(2100);
        when(registry.anchorStatement(anyString(), any(Credentials.class))).thenReturn("test-tx");
        assertEquals(2100, typeB().anchor(requestB()).get("qrByteLength"));
        verify(registry, times(1)).anchorStatement(anyString(), any(Credentials.class));
    }
}
