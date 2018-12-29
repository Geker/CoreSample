package org.PdfTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImage;
import com.lowagie.text.pdf.PdfIndirectObject;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * Hello world!
 *
 */
public class SealGen2 {
    private static final String DEST = "D:/tmp/gz/result.pdf";
    private static final String FONT = "STSong-Light";
    private static final String ENCODING = "UniGB-UCS2-H";
    public static final String KEYSTORE = "F:\\ZzCert\\test.p12";
    public static final char[] PASSWORD = "111111".toCharArray();// keystory密码
    public static final String SRC = "D:\\tmp\\gz\\关于部分运营单据实行系统套打印章的请示.pdf";
    public static final  String  IMG="D:\\tmp\\gz\\seal.png";
    // public static final String DEST = "F:\\test\\signed_dest.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SealGen2().manipulatePdf(SRC, DEST);
    }

    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        Image image = Image.getInstance(IMG);
        PdfImage stream = new PdfImage(image, "seal", null);
        stream.put(new PdfName("ITXT_Seal"), new PdfName("123456789"));
        PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
        image.setDirectReference(ref.getIndirectReference());
        image.setAbsolutePosition(400, 100);
        int[] transparency=new int[] {1,2};
        image.setTransparency(transparency);
        PdfContentByte over = stamper.getOverContent(3);
        image.scaleToFit(150, 150);
        
        over.addImage(image);
        stamper.close();
        reader.close();
    }
}
