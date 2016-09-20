package demo;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);
        Handlers handlers = new Handlers();

        JsonObject config = new JsonObject()
                .put("public-key", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhRFdKYS46j0/lBDyw/FhVpNQk1tjwNERsop9Sg5wRe1ZDpnWa9sOwxzt4AwSzw8XR/0hukx2QbIrSOEQP98Q3d5nW3QFaiTsX+/f10Hvfsr8gA7xneoemRaxP+4Ed+2QpcsuevXY30NBKg67cXfy3jMgXCpKJzlza36pyvakrWIc0Gr7NWYZB0RIMb/jx6lVd1O/2sopytMC+LqzL15xK8zBBRx6jtiO2v2VA54iAiLvcQWmipa8BZTaMRVy26k+rZ0k/6NO9JwTSpOsmecNkT05OB7vU4MrXfFvifsZsH3IZaS+Hnnc/Px0SMkH/FyfJbo/kbTRiFasy8/gMlCPVwIDAQAB")
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
