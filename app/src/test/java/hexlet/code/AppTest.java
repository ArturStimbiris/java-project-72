package hexlet.code;

import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.javalin.testtools.JavalinTest;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    private static Javalin app;

    @BeforeAll
    public static void beforeAll() {
        app = App.getApp();
    }

    @AfterAll
    public static void afterAll() {
        app.stop();
        Unirest.shutDown();
    }

    @Test
    void testRoot() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }
}
