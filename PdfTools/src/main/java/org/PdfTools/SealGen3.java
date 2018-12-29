package org.PdfTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.HashMap;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfDate;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfLiteral;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfString;

/**
 * Hello world!
 *
 */
public class SealGen3 {
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
        SealGen3.sign(SRC, String.format(DEST, 3));
        // app.sign(SRC, String.format(DEST, 4), chain, pk, DigestAlgorithms.RIPEMD160, provider.getName(), CryptoStandard.CADES, "Test 4",
        // "Ghent");
    }

    public static void sign(String src, String dest) throws GeneralSecurityException, IOException, DocumentException {

        PdfReader reader = new PdfReader(src);

        Image image = Image.getInstance("D:\\tmp\\gz\\seal2.png");
        PdfObject overrideFileId = new PdfLiteral("<123><123>".getBytes());



        FileOutputStream fout = new FileOutputStream(DEST);
        PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0', new File("/temp"), true);

        PdfSignatureAppearance sap = stp.getSignatureAppearance();

        sap.setLayer2Text("");
        sap.setLayer4Text("");



        Calendar signDate=Calendar.getInstance();

        stp.setEnforcedModificationDate(signDate);
        stp.setOverrideFileId(overrideFileId);


        PdfDictionary dic = new PdfDictionary();
        dic.put(PdfName.FILTER, PdfName.ADOBE_PPKLITE);
        dic.put(PdfName.M, new PdfDate(signDate));

        sap.setCryptoDictionary(dic);
        sap.setSignDate(signDate);
        sap.setImage(image);

        sap.setVisibleSignature(new Rectangle(400, 100,  520, 220), 1);

        stp.close();


    }
}
