package hexlet.code.repository;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class UrlCheckRepositoryTest extends TestBase {

    private Url testUrl;

    /**
     * Sets up the test environment before each test method execution.
     * Initializes a test URL in the database.
     *
     * @throws SQLException if any database error occurs during setup
     */
    @BeforeEach
    public void setUp() throws SQLException {
        testUrl = new Url("https://example.com");
        UrlRepository.save(testUrl);
    }

    @Test
    void testSave() throws SQLException {
        UrlCheck check = new UrlCheck();
        check.setUrlId(testUrl.getId());
        check.setStatusCode(200);
        check.setTitle("Test Title");
        check.setH1("Test H1");
        check.setDescription("Test Description");
        UrlCheckRepository.save(check);
        assertThat(check.getId()).isNotNull();
    }

    @Test
    void testFindByUrlId() throws SQLException {
        UrlCheck check = new UrlCheck();
        check.setUrlId(testUrl.getId());
        check.setStatusCode(200);
        UrlCheckRepository.save(check);

        List<UrlCheck> checks = UrlCheckRepository.findByUrlId(testUrl.getId());
        assertThat(checks).hasSize(1);
        assertThat(checks.get(0).getStatusCode()).isEqualTo(200);
    }

    @Test
    void testFindLastCheckByUrlId() throws SQLException {
        UrlCheck check1 = new UrlCheck();
        check1.setUrlId(testUrl.getId());
        check1.setStatusCode(200);
        UrlCheckRepository.save(check1);

        UrlCheck check2 = new UrlCheck();
        check2.setUrlId(testUrl.getId());
        check2.setStatusCode(404);
        UrlCheckRepository.save(check2);

        UrlCheck lastCheck = UrlCheckRepository.findLastCheckByUrlId(testUrl.getId());
        assertThat(lastCheck).isNotNull();
        assertThat(lastCheck.getStatusCode()).isEqualTo(404);
    }
}
