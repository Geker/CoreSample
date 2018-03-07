package org.embedJetty;

import org.eclipse.jetty.server.Server;

public class SimpleServer {

	public static void main(String[] args) {

		Server server = new Server(8080);
		try {

			server.setHandler(new HelloHandler("ni hao!", "Beijing"));
			server.start();
			server.dumpStdErr();
			server.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
