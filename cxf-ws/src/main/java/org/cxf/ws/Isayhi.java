package org.cxf.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Hello world!
 *
 */
@WebService
public interface Isayhi {


    @WebMethod
    String sayhi(@WebParam(name = "username") String name);
}
