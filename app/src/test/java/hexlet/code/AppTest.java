package hexlet.code;

import hexlet.code.App;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    private static Javalin app;
    private static String baseUrl;

    @BeforeAll
    public static void beforeAll() throws Exception {
        app = App.getApp();
        app.start(0);
        int port = app.port();
        baseUrl = "http://localhost:" + port;

        Unirest.config()
            .socketTimeout(5000)
            .connectTimeout(5000)
            .concurrency(10, 5)
            .setDefaultHeader("Accept", "text/html")
            .setDefaultHeader("User-Agent", "Mozilla/5.0 (compatible; MyApp/1.0)");
    }

    @AfterAll
    public static void afterAll() {
        app.stop();
        Unirest.shutDown();
    }

    @Test
    void testRoot() {
        try {
            var response = Unirest.get(baseUrl).asString();
            assertThat(response.getStatus()).isEqualTo(200);
            assertThat(response.getBody()).contains("Анализатор страниц");
        } catch (UnirestException e) {
            throw new RuntimeException("Unirest request failed", e);
        }
    }
}
