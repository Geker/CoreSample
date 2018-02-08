
package org.cxf.ws.client;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 3.2.0
 * 2017-10-31T17:38:14.707+08:00
 * Generated source version: 3.2.0
 *
 */
public final class Isayhi_SayhiPort_Client {

    private static final QName SERVICE_NAME = new QName("http://ws.cxf.org/", "SayhiService");

    private Isayhi_SayhiPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = SayhiService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        SayhiService ss = new SayhiService(wsdlURL, SERVICE_NAME);
        Isayhi port = ss.getSayhiPort();

        {
        System.out.println("Invoking sayhi...");
            java.lang.String _sayhi_username = "xiiixx";
        java.lang.String _sayhi__return = port.sayhi(_sayhi_username);
        System.out.println("sayhi.result=" + _sayhi__return);


        }

        System.exit(0);
    }

}