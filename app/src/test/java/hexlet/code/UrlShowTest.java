package hexlet.code.controllers;

import hexlet.code.TestBase;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.HttpStatus;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlShowTest extends TestBase {

    @Test
    void testShowUrl() throws SQLException, IOException {
        JavalinTest.test(app, (server, client) -> {
            System.out.println("=== Starting testShowUrl ===");
            
            // Добавление тестовых данных
            Url url = new Url("https://show-test.com");
            UrlRepository.save(url);
            long id = url.getId();
            System.out.println("Created URL with ID: " + id);
            
            Response response = client.get("/urls/" + id);
            System.out.println("Response code: " + response.code());
            String body = response.body().string();
            System.out.println("Response body: " + body);
            
            assertThat(response.code())
                .as("Check response code")
                .isEqualTo(HttpStatus.OK.getCode());
                
            assertThat(body)
                .as("Check body contains URL")
                .contains("https://show-test.com");
                
            assertThat(body)
                .as("Check body contains 'Детали URL'")
                .contains("Детали URL");
                
            System.out.println("=== Finished testShowUrl ===");
        });
    }
}
