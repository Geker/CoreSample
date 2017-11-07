package org.cxf.ws.server;

import org.apache.cxf.endpoint.ServerImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.http_jetty.JettyHTTPDestination;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngine;
import org.cxf.ws.Sayhi;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.junit.Test;

public class StartCxfSvrTest {
	/**
	 * 通过反射实现Jetty的threadPool的设置
	 *
	 * @throws Exception
	 */
	@Test
	public void testMethod_1() throws Exception {
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
	}

	@Test
	public void testMethod_2() throws Exception {

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
	}
}
