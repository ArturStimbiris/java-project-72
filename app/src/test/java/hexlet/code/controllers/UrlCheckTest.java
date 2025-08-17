package hexlet.code.controllers;

import hexlet.code.TestBase;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.HttpStatus;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlCheckTest extends TestBase {

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testCheckUrl() throws SQLException, IOException {
        String mockBody = "<html><head><title>Test Page</title>"
                        + "<meta name=\"description\" content=\"Test Description\">"
                        + "</head><body><h1>Test Header</h1></body></html>";
        
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockBody)
                .setResponseCode(200));

        String testUrl = mockWebServer.url("/").toString();

        JavalinTest.test(app, (server, client) -> {
            Url url = new Url(testUrl);
            UrlRepository.save(url);
            
            var response = client.post("/urls/" + url.getId() + "/checks");
            
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
            
            var checks = UrlCheckRepository.findByUrlId(url.getId());
            assertThat(checks).hasSize(1);
            
            var check = checks.get(0);
            assertThat(check.getStatusCode()).isEqualTo(200);
            assertThat(check.getTitle()).isEqualTo("Test Page");
            assertThat(check.getH1()).isEqualTo("Test Header");
            assertThat(check.getDescription()).isEqualTo("Test Description");
        });
    }
}
