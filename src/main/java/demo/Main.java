package demo;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);
        Handlers handlers = new Handlers();


        router.route("/api/*").handler(CorsHandler.create("http://localhost:4200/*")
                .allowedMethod(HttpMethod.GET)
                .allowedHeader("Authorization")
                .allowedHeader("Content-Type")
                .allowCredentials(true));

        JsonObject config = new JsonObject()
                .put("public-key", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmu1bWx2D/a5sPoI8g8n/cQxCyRhjolzFJj4RS7HJ1crGBiMFar0CQPX6XYeyU21nTGwJwjdCDYv9PHC9N97LkfjHMEyo928liwcHRt3P7L+JxYZOB76sKAAFQboLEGB8P1jEya9R0LSxclqwu3U6EcIFF3FlGUw7tT4011o8oUrlHvQXjWiTFsoPhsqAG/Ib3rZv242yCgkewBk/jN1Phg+NwQV+/+RzWX6hVrhiLmkpR9ojrnRh63v6rk9cHAcKuhBT0mfYG0YgBegp9JydKPI+SvwwvGFoIBIdVSk9q/Ys/C+x/ytJY0+043LzcES3ehERK4JSy/WxG9I0j4gYxQIDAQAB")
                .put("permissionsClaimKey", "realm_access/roles");
        JWTAuth authProvider = JWTAuth.create(vertx, config);

        router.route("/api/*").handler(JWTAuthHandler.create(authProvider));
        router.route("/api/*").handler(handlers::userLogger);

        StaticHandler staticHandler = StaticHandler.create("webroot/dist");
        router.route("/ui/*").handler(staticHandler::handle);

        router.route("/api/books").handler(handlers::listBooks);

        vertx.createHttpServer().requestHandler(router::accept).listen(8000);

    }
}
