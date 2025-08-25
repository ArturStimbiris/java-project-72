package hexlet.code;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppTest extends TestBase {

    @Test
    void testRoot() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    void testAppInitialization() {
        assertDoesNotThrow(() -> {
            Javalin appInstance = App.getApp();
            assertThat(appInstance).isNotNull();
        });
    }
}
