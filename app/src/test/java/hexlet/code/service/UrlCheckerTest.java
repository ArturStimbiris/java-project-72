package hexlet.code.service;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.assertj.core.api.Assertions.assertThat;

public class UrlCheckerTest {

    private static MockWebServer mockWebServer;
    private static String testUrl;
    private static String testHtmlContent;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        testUrl = mockWebServer.url("/").toString();

        testHtmlContent = Files.readString(Paths.get("src/test/resources/test-checker-page.html"));
        MockResponse response = new MockResponse()
                .setBody(testHtmlContent)
                .setResponseCode(200);
        mockWebServer.enqueue(response);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testCheck() throws Exception {
        Url url = new Url(testUrl);
        url.setId(1L);

        UrlCheck check = UrlChecker.check(url);
        assertThat(check.getStatusCode()).isEqualTo(200);
        assertThat(check.getTitle()).isEqualTo("Test Title");
        assertThat(check.getH1()).isEqualTo("Test H1");
        assertThat(check.getDescription()).isEqualTo("Test Description");
    }
}
