package hexlet.code.controllers;

import hexlet.code.TestBase;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.HttpStatus;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlControllerTest extends TestBase {

    private static MockWebServer mockWebServer;
    private static String testHtmlContent;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        testHtmlContent = Files.readString(Paths.get("src/test/resources/test-page.html"));
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testIndex() throws IOException {
        JavalinTest.test(app, (server, client) -> {
            Response response = client.get("/");
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
            String body = response.body().string();
            assertThat(body).contains("Добавить новый URL");
        });
    }

    @Test
    void testCreateValidUrl() throws SQLException, IOException {
        JavalinTest.test(app, (server, client) -> {
            String form = "url=https://example.com";
            Response response = client.post("/urls", form);

            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
            String body = response.body().string();
            assertThat(body).contains("Список добавленных URL");
            assertThat(body).contains("https://example.com");

            List<Url> urls = UrlRepository.getEntities();
            assertThat(urls).hasSize(1);
            assertThat(urls.get(0).getName()).contains("example.com");
        });
    }

    @Test
    void testCreateInvalidUrl() throws SQLException, IOException {
        JavalinTest.test(app, (server, client) -> {
            String form = "url=invalid";
            Response response = client.post("/urls", form);

            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
            String body = response.body().string();
            assertThat(body).contains("<h1 class=\"mb-4\">Добавить новый URL</h1>");

            List<Url> urls = UrlRepository.getEntities();
            assertThat(urls).isEmpty();
        });
    }

    @Test
    void testShowUrl() throws SQLException, IOException {
        JavalinTest.test(app, (server, client) -> {
            Url url = new Url("https://show-test.com");
            UrlRepository.save(url);
            long id = url.getId();

            Response response = client.get("/urls/" + id);
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
            String body = response.body().string();
            assertThat(body).contains("https://show-test.com");
            assertThat(body).contains("Детали URL");
        });
    }

    @Test
    void testListUrls() throws SQLException, IOException {
        JavalinTest.test(app, (server, client) -> {
            Url url1 = new Url("https://test1.com");
            Url url2 = new Url("https://test2.com");
            UrlRepository.save(url1);
            UrlRepository.save(url2);

            Response response = client.get("/urls");
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
            String body = response.body().string();
            assertThat(body).contains("https://test1.com");
            assertThat(body).contains("https://test2.com");
        });
    }

    @Test
    void testCheckUrl() throws SQLException, IOException {
        mockWebServer.enqueue(new MockResponse()
                .setBody(testHtmlContent)
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
