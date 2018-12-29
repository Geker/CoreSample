package org.PdfTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;

/**
 * Hello world!
 *
 */
public class SealGen {
    private static final String DEST = "D:/tmp/gz/result.pdf";
    private static final String FONT = "STSong-Light";
    private static final String ENCODING = "UniGB-UCS2-H";
    public static final String KEYSTORE = "F:\\ZzCert\\test.p12";
    public static final char[] PASSWORD = "111111".toCharArray();// keystory密码
    public static final String SRC = "D:\\tmp\\gz\\关于部分运营单据实行系统套打印章的请示.pdf";
    // public static final String DEST = "F:\\test\\signed_dest.pdf";

    public static void main(String[] args) throws DocumentException, IOException, GeneralSecurityException {
        // KeyStore ks = KeyStore.getInstance("PKCS12");
        // ks.load(new FileInputStream(KEYSTORE), PASSWORD);
        // String alias = (String) ks.aliases().nextElement();
        // PrivateKey pk = (PrivateKey) ks.getKey(alias, PASSWORD);
        // Certificate[] chain = ks.getCertificateChain(alias);
        // new一个上边自定义的方法对象，调用签名方法
        // MainWindow app = new MainWindow();
        // app.sign(SRC, String.format(DEST, 1), chain, pk, DigestAlgorithms.SHA1, provider.getName(), CryptoStandard.CMS, "Test 1",
        // "Ghent");
        // app.sign(SRC, String.format(DEST, 2), chain, pk, "SM3", provider.getName(), CryptoStandard.CADES, "Test 2", "Ghent");
        SealGen.sign(SRC, String.format(DEST, 3));
        // app.sign(SRC, String.format(DEST, 4), chain, pk, DigestAlgorithms.RIPEMD160, provider.getName(), CryptoStandard.CADES, "Test 4",
        // "Ghent");
    }

    public static void sign(String src, String dest) throws GeneralSecurityException, IOException, DocumentException {

        PdfReader reader = new PdfReader(src);

        Image image = Image.getInstance("D:\\tmp\\gz\\seal2.png");

        KeyStore ks = KeyStore.getInstance("pkcs12");
        ks.load(new FileInputStream("D:\\tmp\\gz\\test.keystore"), "abc123".toCharArray());
        String alias = (String) ks.aliases().nextElement();
        PrivateKey key = (PrivateKey) ks.getKey(alias, "abc123".toCharArray());
        Certificate[] chain = ks.getCertificateChain(alias);
        // PdfReader reader1 = new PdfReader("original.pdf");
        // PdfReader reader = new PdfReader(src);

        FileOutputStream fout = new FileOutputStream(DEST);
        PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0', new File("/temp"), true);

        PdfSignatureAppearance sap = stp.getSignatureAppearance();
        sap.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
        sap.setCrypto(key, chain, null, PdfSignatureAppearance.WINCER_SIGNED);
        sap.setReason("I'm the author");
        sap.setLocation("bj");
        sap.setLayer2Text("");
        sap.setLayer4Text("");
//        sap.setAcro6Layers(false);
        // comment next line to have an invisible signature
        sap.setVisibleSignature(new Rectangle(400, 100,  520, 220),3, null);
        sap.setSignatureGraphic(image);
       
//        sap.setImage(image);
        stp.close();

    }
}
