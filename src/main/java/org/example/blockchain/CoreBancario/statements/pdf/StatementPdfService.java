package org.example.blockchain.CoreBancario.statements.pdf;

import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.example.blockchain.CoreBancario.banking.model.BankTransaction;
import org.example.blockchain.CoreBancario.statements.StatementData;
import org.example.blockchain.CoreBancario.statements.qr.QrCodeService;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Monta um PDF legivel a partir do mesmo snapshot e do MESMO envelope CBOR ja certificado.
 * O hash na blockchain autentica o conteudo CBOR, nao os bytes deste PDF.
 */
@Service
@RequiredArgsConstructor
public class StatementPdfService {
    private static final PDRectangle PAGE = PDRectangle.A4;
    private static final float LEFT = 48f;
    private static final float RIGHT = PAGE.getWidth() - LEFT;
    private static final PDFont REGULAR = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
    private static final PDFont BOLD = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Color BLUE = new Color(22, 103, 194);
    private static final Color DARK = new Color(23, 35, 58);
    private static final Color MUTED = new Color(82, 97, 117);
    private final QrCodeService qrCodes;

    public byte[] generate(StatementData data, String type, String bankName,
                           Map<String, Object> certified) throws IOException {
        if (!"A".equals(type) && !"B".equals(type)) {
            throw new IllegalArgumentException("Tipo de extracto invalido");
        }
        Object base64 = certified.get("qrCodeDataBase64");
        if (!(base64 instanceof String encoded) || encoded.isBlank()) {
            throw new IllegalArgumentException("A certificacao nao devolveu o envelope CBOR");
        }
        byte[] envelope;
        try {
            envelope = Base64.getDecoder().decode(encoded);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Envelope CBOR Base64 invalido", ex);
        }
        // Validar o QR ANTES de escrever qualquer pagina. Preserva os bytes assinados.
        BitMatrix qrMatrix = qrCodes.encodeEnvelope(envelope);

        try (PDDocument document = new PDDocument()) {
            document.getDocumentInformation().setTitle("Extracto bancario - " + safe(bankName));
            document.getDocumentInformation().setSubject("Comprovativo do conteudo bancario certificado em CBOR");
            try (PageWriter writer = new PageWriter(document, bankName)) {
                writer.title("Extracto bancario - Tipo " + type);
                writer.paragraph("Documento de demonstracao. Valores obtidos do core bancario.", MUTED, 10f);
                writer.gap(17);
                writer.detail("Titular", data.holder());
                writer.detail("Numero de conta", data.accountNo());
                writer.detail("Periodo", DATE.format(data.startDate()) + " a " + DATE.format(data.endDate()));
                writer.detail("Moeda", data.currency());
                writer.detail("Saldo final na data de fim", money(data.closingBalance(), data.currency()));
                writer.gap(18);

                if ("B".equals(type)) {
                    writer.section("Movimentos do periodo");
                    writeMovements(writer, data.movements(), data.currency());
                } else {
                    writer.section("Resumo");
                    writer.paragraph("Extracto Tipo A: os movimentos individuais nao fazem parte do conteudo CBOR certificado.", MUTED, 10f);
                }

                // Pagina dedicada: QR maior e sem distorcao, para leitura em papel.
                writer.newPage();
                writer.title("Certificacao do extracto");
                writer.paragraph("O QR Code contem o envelope CBOR binario assinado do Tipo " + type + ".", DARK, 10f);
                writer.gap(12);
                writer.qr(qrMatrix, 290f);
                writer.gap(12);
                writer.section("Referencia da certificacao");
                writer.detail("Emitido em (UTC)", text(certified.get("issuedAt")));
                writer.detailWrapped("Hash do documento", text(certified.get("documentHash")));
                writer.detailWrapped("Transaccao blockchain", text(certified.get("transactionHash")));
                writer.gap(12);
                writer.paragraph("A blockchain e a assinatura permitem verificar os dados CBOR. Este PDF e uma representacao visual desses dados; nao e, por si, o objecto assinado.", MUTED, 9f);
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        }
    }

    private void writeMovements(PageWriter writer, List<BankTransaction> movements, String currency) throws IOException {
        if (movements == null || movements.isEmpty()) {
            writer.paragraph("Nao foram encontrados movimentos no periodo.", MUTED, 10f);
            return;
        }
        writer.transactionHeader();
        for (BankTransaction tx : movements) {
            String kind = "C".equals(tx.direction()) ? "Credito" : "D".equals(tx.direction()) ? "Debito" : tx.direction();
            writer.transactionRow(DATE.format(tx.date()), kind,
                money(tx.amount(), currency), tx.description());
        }
    }

    private static String money(BigDecimal amount, String currency) {
        return (amount == null ? "0.00" : amount.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString())
            + " " + currency;
    }

    private static String text(Object value) {
        return value == null ? "-" : String.valueOf(value);
    }

    private static String safe(String value) {
        if (value == null) return "-";
        StringBuilder out = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch == '\u2013' || ch == '\u2014') out.append('-');
            else if (ch >= 32 && ch <= 255 && ch != 127) out.append(ch);
            else if (ch == '\t' || ch == '\n') out.append(' ');
            else out.append('?');
        }
        return out.toString();
    }

    private static String fit(String raw, PDFont font, float fontSize, float maxWidth) throws IOException {
        String text = safe(raw);
        if (font.getStringWidth(text) * fontSize / 1000f <= maxWidth) return text;
        while (!text.isEmpty() && font.getStringWidth(text + "...") * fontSize / 1000f > maxWidth) {
            text = text.substring(0, text.length() - 1);
        }
        return text + "...";
    }

    /** Um pequeno layout writer, com quebra de pagina nas tabelas de movimentos. */
    private static final class PageWriter implements AutoCloseable {
        private final PDDocument document;
        private final String bank;
        private PDPageContentStream stream;
        private float y;
        private int pageNumber = 0;

        PageWriter(PDDocument document, String bankName) throws IOException {
            this.document = document;
            this.bank = safe(bankName == null || bankName.isBlank() ? "Banco de Testes" : bankName);
            newPage();
        }

        void newPage() throws IOException {
            if (stream != null) { footer(); stream.close(); }
            PDPage page = new PDPage(PAGE);
            document.addPage(page);
            stream = new PDPageContentStream(document, page);
            pageNumber++;
            y = PAGE.getHeight() - 56;
            draw(bank, LEFT, y, BOLD, 17f, BLUE);
            y -= 12;
            line(y, new Color(213, 224, 237));
            y -= 34;
        }

        void title(String title) throws IOException {
            ensure(40);
            draw(fit(title, BOLD, 16f, RIGHT - LEFT), LEFT, y, BOLD, 16f, DARK);
            y -= 27;
        }

        void section(String title) throws IOException {
            ensure(37);
            draw(title, LEFT, y, BOLD, 12f, BLUE);
            y -= 25;
        }

        void detail(String label, String value) throws IOException {
            ensure(25);
            draw(label + ":", LEFT, y, BOLD, 10f, DARK);
            draw(fit(value, REGULAR, 10f, 325), LEFT + 170, y, REGULAR, 10f, DARK);
            y -= 24;
        }

        void detailWrapped(String label, String value) throws IOException {
            ensure(38);
            draw(label + ":", LEFT, y, BOLD, 9f, DARK);
            y -= 15;
            String str = safe(value);
            // Hashes hexadecimais sao divididos em duas linhas para nao cortar caracteres.
            for (int i = 0; i < str.length(); i += 70) {
                ensure(17);
                draw(str.substring(i, Math.min(str.length(), i + 70)), LEFT, y, REGULAR, 8f, MUTED);
                y -= 15;
            }
            y -= 5;
        }

        void paragraph(String message, Color color, float size) throws IOException {
            ensure(23);
            draw(fit(message, REGULAR, size, RIGHT - LEFT), LEFT, y, REGULAR, size, color);
            y -= 21;
        }

        void gap(float points) { y -= points; }

        void transactionHeader() throws IOException {
            ensure(29);
            draw("Data", LEFT, y, BOLD, 9f, MUTED);
            draw("Tipo", LEFT + 88, y, BOLD, 9f, MUTED);
            draw("Valor", LEFT + 154, y, BOLD, 9f, MUTED);
            draw("Descricao", LEFT + 280, y, BOLD, 9f, MUTED);
            y -= 9;
            line(y, new Color(220, 229, 238));
            y -= 16;
        }

        void transactionRow(String date, String kind, String value, String description) throws IOException {
            if (y < 78f) {
                newPage();
                section("Movimentos (continuacao)");
                transactionHeader();
            }
            draw(date, LEFT, y, REGULAR, 9f, DARK);
            draw(fit(kind, REGULAR, 9f, 64), LEFT + 88, y, REGULAR, 9f, DARK);
            draw(fit(value, REGULAR, 9f, 117), LEFT + 154, y, REGULAR, 9f, DARK);
            draw(fit(description, REGULAR, 9f, RIGHT - LEFT - 280), LEFT + 280, y, REGULAR, 9f, DARK);
            y -= 18;
        }

        void qr(BitMatrix matrix, float size) throws IOException {
            ensure(size + 22);
            float x = (PAGE.getWidth() - size) / 2f;
            float lowerY = y - size;
            stream.setNonStrokingColor(Color.WHITE);
            stream.addRect(x, lowerY, size, size);
            stream.fill();
            float module = size / matrix.getWidth();
            stream.setNonStrokingColor(Color.BLACK);
            for (int row = 0; row < matrix.getHeight(); row++) {
                for (int col = 0; col < matrix.getWidth(); col++) {
                    if (matrix.get(col, row)) {
                        stream.addRect(x + col * module,
                            lowerY + (matrix.getHeight() - 1 - row) * module,
                            module + 0.01f, module + 0.01f);
                    }
                }
            }
            stream.fill();
            y = lowerY - 19;
        }

        void ensure(float height) throws IOException {
            if (y - height < 60f) newPage();
        }

        private void draw(String text, float x, float baseline, PDFont font, float size, Color color) throws IOException {
            stream.beginText();
            stream.setNonStrokingColor(color);
            stream.setFont(font, size);
            stream.newLineAtOffset(x, baseline);
            stream.showText(safe(text));
            stream.endText();
        }

        private void line(float atY, Color color) throws IOException {
            stream.setStrokingColor(color);
            stream.moveTo(LEFT, atY);
            stream.lineTo(RIGHT, atY);
            stream.stroke();
        }

        private void footer() throws IOException {
            stream.setStrokingColor(new Color(224, 232, 241));
            stream.moveTo(LEFT, 42f);
            stream.lineTo(RIGHT, 42f);
            stream.stroke();
            draw("Demonstracao academica - Dados ficticios", LEFT, 27f, REGULAR, 8f, MUTED);
            draw("Pagina " + pageNumber, RIGHT - 46, 27f, REGULAR, 8f, MUTED);
        }

        @Override public void close() throws IOException {
            if (stream != null) { footer(); stream.close(); stream = null; }
        }
    }
}
