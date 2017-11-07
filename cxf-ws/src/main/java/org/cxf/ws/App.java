package org.cxf.ws;

import org.apache.cxf.endpoint.ServerImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.http_jetty.JettyHTTPDestination;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngine;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class App {
	public static void main(String[] args) throws Exception {
		// 第一种发布方式：通过CXF提供的JaxWsServerFactoryBean来发布webservice
		JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
		factory.setServiceClass(Sayhi.class);
		factory.setAddress("http://localhost:8080/sayhi");
		ServerImpl server = (ServerImpl) factory.create();
		JettyHTTPDestination jtdest = (JettyHTTPDestination) server.getDestination();
		JettyHTTPServerEngine eng = (JettyHTTPServerEngine) jtdest.getEngine();
		QueuedThreadPool qtp = (QueuedThreadPool) eng.getServer().getThreadPool();
		qtp.setMinThreads(100);
		qtp.setMaxThreads(300);
		server.start();

		// // // 第二种方式，通过JAX-WS提供的Endpoint来发布webservice
		// // // 首先创建webservice服务提供类的实例
		// Sayhi implementor = new Sayhi();
		// String address = "http://localhost:8080/sayhi";
		//
		// Endpoint end = Endpoint.publish(address, implementor);

		//

		// SpringJettyHTTPServerEngineFactory
	}

}
