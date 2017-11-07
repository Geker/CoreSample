package org.cxf.ws;

import org.apache.cxf.endpoint.ServerImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.http_jetty.JettyHTTPDestination;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngine;

public class App {
	public static void main(String[] args) throws Exception {
		// 第一种发布方式：通过CXF提供的JaxWsServerFactoryBean来发布webservice
		JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();

		factory.setServiceClass(Sayhi.class);
		// Server jtSvr = new Server(8888);

		// jtSvr.start();
		factory.setAddress("http://localhost:8080/sayhi");


		ServerImpl server = (ServerImpl) factory.create();
		JettyHTTPDestination jtdest = (JettyHTTPDestination) server.getDestination();
		JettyHTTPServerEngine eng = (JettyHTTPServerEngine) jtdest.getEngine();
		// eng.setServer(jtSvr);
		server.start();

		// // // 第二种方式，通过JAX-WS提供的Endpoint来发布webservice
		// // // 首先创建webservice服务提供类的实例
		// Sayhi implementor = new Sayhi();
		// String address = "http://localhost:8080/sayhi";
		//
		// Endpoint end = Endpoint.publish(address, implementor);
		//

	}

}
