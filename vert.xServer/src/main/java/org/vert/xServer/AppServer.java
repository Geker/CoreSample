package org.vert.xServer;

import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * Hello world!
 *
 */
public class AppServer {
	public static void main(String[] args) {

		Computer com = new Computer();
		com.setCpu("i7 8700k");
		com.setKeyboard("coolmaster");
		com.setMem("8g");
		com.setMouse("logi");
		com.setPrice(new BigDecimal(8999));
		ObjectMapper obMapper = new ObjectMapper();
		Vertx vertx = Vertx.vertx();
		HttpServer server = vertx.createHttpServer();

		Router router = Router.router(vertx);

		router.route("/com").handler(routingContext -> {

			// This handler will be called for every request
			HttpServerResponse response = routingContext.response();
			response.setChunked(true);
			response.putHeader("content-type", "application/json;charset=utf-8");

			// Write to the response and end it
			try {
				response.write(obMapper.writeValueAsString(com));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.end();

		});

		server.requestHandler(router::accept).listen(8003);
	}
}
