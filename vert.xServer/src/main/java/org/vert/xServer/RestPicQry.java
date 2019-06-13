package org.vert.xServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class RestPicQry {
    public static void main(String[] args) {
        ObjectMapper obMapper = new ObjectMapper();

        Vertx vertx = Vertx.factory.vertx();
        Router router = Router.router(vertx);
        // add a handler which sets the request body on the RoutingContext.
         router.route().handler(BodyHandler.create());
        // expose a POST method endpoint on the URI: /analyze
        router.post("/analyze").handler((context) -> {
            JsonObject body = context.getBodyAsJson();

            // a JsonObject wraps a map and it exposes type-aware getters
            String postedText = body.getString("text");

            context.response().end("You POSTed JSON which contains a text attribute with the value: " + postedText);

        });

        Handler<HttpServerRequest> handler = new Handler<HttpServerRequest>() {

            @Override
            public void handle(HttpServerRequest event) {

                try {
                    System.out.println(obMapper.writeValueAsString(event));
                }
                catch (JsonProcessingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        };
        vertx.createHttpServer().requestHandler(router).requestHandler(handler).listen(8080);

    }

}
