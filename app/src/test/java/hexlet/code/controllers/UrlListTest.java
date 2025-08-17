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

public class UrlListTest extends TestBase {

    @Test
    void testListUrls() throws SQLException, IOException {
        JavalinTest.test(app, (server, client) -> {
            System.out.println("=== Starting testListUrls ===");
            
            // Добавление тестовых данных
            Url url1 = new Url("https://test1.com");
            Url url2 = new Url("https://test2.com");
            UrlRepository.save(url1);
            UrlRepository.save(url2);
            System.out.println("Added 2 URLs to database");
            
            Response response = client.get("/urls");
            System.out.println("Response code: " + response.code());
            String body = response.body().string();
            System.out.println("Response body: " + body);
            
            assertThat(response.code())
                .as("Check response code")
                .isEqualTo(HttpStatus.OK.getCode());
                
            assertThat(body)
                .as("Check body contains first URL")
                .contains("https://test1.com");
                
            assertThat(body)
                .as("Check body contains second URL")
                .contains("https://test2.com");
                
            System.out.println("=== Finished testListUrls ===");
        });
    }
}
