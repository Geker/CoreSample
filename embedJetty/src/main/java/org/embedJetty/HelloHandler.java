package org.embedJetty;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.HomeBaseWarning;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class HelloHandler extends AbstractHandler {

	private static final Logger LOG = Log.getLogger(HomeBaseWarning.class);

	final String greeting;
	final String body;

	public HelloHandler() {
		this("Hello World");
	}

	public HelloHandler(String greeting) {
		this(greeting, null);
	}

	public HelloHandler(String greeting, String body) {
		this.greeting = greeting;
		this.body = body;
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		LOG.debug("target:" + target);
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		PrintWriter out = response.getWriter();

		out.println("<h1>" + greeting + "</h1>");
		if (body != null) {
			out.println(body);
			out.print("<br/>");
			out.println(request.getParameterMap().toString());
		}

		baseRequest.setHandled(true);
	}

}
