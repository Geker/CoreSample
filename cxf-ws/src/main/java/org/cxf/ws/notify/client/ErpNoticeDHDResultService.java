package org.cxf.ws.notify.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.2.0
 * 2017-11-07T15:09:14.306+08:00
 * Generated source version: 3.2.0
 * 
 */
@WebServiceClient(name = "ErpNoticeDHDResultService", 
                  wsdlLocation = "http://10.60.81.145:20080/erpResultNotify?wsdl",
                  targetNamespace = "http://service.ws.gw.sales.ec.org/") 
public class ErpNoticeDHDResultService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://service.ws.gw.sales.ec.org/", "ErpNoticeDHDResultService");
    public final static QName ErpNoticeDHDResultPort = new QName("http://service.ws.gw.sales.ec.org/", "ErpNoticeDHDResultPort");
    static {
        URL url = null;
        try {
            url = new URL("http://10.60.81.145:20080/erpResultNotify?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ErpNoticeDHDResultService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://10.60.81.145:20080/erpResultNotify?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ErpNoticeDHDResultService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ErpNoticeDHDResultService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ErpNoticeDHDResultService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public ErpNoticeDHDResultService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public ErpNoticeDHDResultService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ErpNoticeDHDResultService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns ErpNoticeDHDResult
     */
    @WebEndpoint(name = "ErpNoticeDHDResultPort")
    public ErpNoticeDHDResult getErpNoticeDHDResultPort() {
        return super.getPort(ErpNoticeDHDResultPort, ErpNoticeDHDResult.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ErpNoticeDHDResult
     */
    @WebEndpoint(name = "ErpNoticeDHDResultPort")
    public ErpNoticeDHDResult getErpNoticeDHDResultPort(WebServiceFeature... features) {
        return super.getPort(ErpNoticeDHDResultPort, ErpNoticeDHDResult.class, features);
    }

}
