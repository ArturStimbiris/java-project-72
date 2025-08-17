package hexlet.code.controllers;

import hexlet.code.TestBase;
import io.javalin.http.HttpStatus;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class RootControllerTest extends TestBase {
    @Test
    void testIndex() throws IOException {
        JavalinTest.test(app, (server, client) -> {
            System.out.println("=== Starting testIndex ===");
            
            Response response = client.get("/");
            System.out.println("Response code: " + response.code());
            String body = response.body().string();
            System.out.println("Response body: " + body);
            
            assertThat(response.code())
                .as("Check response code")
                .isEqualTo(HttpStatus.OK.getCode());
                
            assertThat(body)
                .as("Check body contains 'Добавить новый URL'")
                .contains("Добавить новый URL");
                
            System.out.println("=== Finished testIndex ===");
        });
    }
}
