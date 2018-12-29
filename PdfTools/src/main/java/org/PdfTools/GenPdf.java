package org.PdfTools;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Hello world!
 *
 */
public class GenPdf {
    private static final String DEST = "D:/tmp/q.pdf";
    private static final String FONT = "STSong-Light";
    private static final String ENCODING = "UniGB-UCS2-H";

    public static void main(String[] args) throws DocumentException, IOException {
        BaseFont bfChinese = BaseFont.createFont(FONT, ENCODING, BaseFont.NOT_EMBEDDED);
        Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
        document.open();
        document.add(new Paragraph("hello world,输出PDF", FontChinese));
        document.close();
        writer.close();
    }
}
