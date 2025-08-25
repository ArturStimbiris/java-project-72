package hexlet.code.repository;

import hexlet.code.model.Url;
import hexlet.code.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

public class UrlRepositoryTest extends TestBase {

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
        Url newUrl = new Url("https://test.com");
        UrlRepository.save(newUrl);
        assertThat(newUrl.getId()).isNotNull();
    }

    @Test
    void testFindByName() throws SQLException {
        Optional<Url> found = UrlRepository.findByName(testUrl.getName());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(testUrl.getName());
    }

    @Test
    void testFindById() throws SQLException {
        Optional<Url> found = UrlRepository.findById(testUrl.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(testUrl.getName());
    }

    @Test
    void testGetEntities() throws SQLException {
        List<Url> urls = UrlRepository.getEntities();
        assertThat(urls).hasSize(1);
        assertThat(urls.get(0).getName()).isEqualTo(testUrl.getName());
    }
}
