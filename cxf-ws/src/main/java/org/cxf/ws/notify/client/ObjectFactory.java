
package org.cxf.ws.notify.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.cxf.ws.notify.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NoticeDHD_QNAME = new QName("http://service.ws.gw.sales.ec.org/", "noticeDHD");
    private final static QName _NoticeDHDResponse_QNAME = new QName("http://service.ws.gw.sales.ec.org/", "noticeDHDResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.cxf.ws.notify.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NoticeDHD }
     * 
     */
    public NoticeDHD createNoticeDHD() {
        return new NoticeDHD();
    }

    /**
     * Create an instance of {@link NoticeDHDResponse }
     * 
     */
    public NoticeDHDResponse createNoticeDHDResponse() {
        return new NoticeDHDResponse();
    }

    /**
     * Create an instance of {@link InterfaceResult }
     * 
     */
    public InterfaceResult createInterfaceResult() {
        return new InterfaceResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoticeDHD }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.gw.sales.ec.org/", name = "noticeDHD")
    public JAXBElement<NoticeDHD> createNoticeDHD(NoticeDHD value) {
        return new JAXBElement<NoticeDHD>(_NoticeDHD_QNAME, NoticeDHD.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoticeDHDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.gw.sales.ec.org/", name = "noticeDHDResponse")
    public JAXBElement<NoticeDHDResponse> createNoticeDHDResponse(NoticeDHDResponse value) {
        return new JAXBElement<NoticeDHDResponse>(_NoticeDHDResponse_QNAME, NoticeDHDResponse.class, null, value);
    }

}
