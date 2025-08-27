package hexlet.code.repository;

import hexlet.code.TestBase;
import hexlet.code.model.Url;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlRepositoryTest extends TestBase {

    @Test
    void testEmptyGetEntities() throws SQLException {
        List<Url> urls = UrlRepository.getEntities();
        assertThat(urls).isEmpty();
    }

    @Test
    void testFindByNameNotFound() throws SQLException {
        Optional<Url> found = UrlRepository.findByName("http://nonexistent.test");
        assertThat(found).isEmpty();
    }

    @Test
    void testFindByIdNotFound() throws SQLException {
        Optional<Url> found = UrlRepository.findById(999L);
        assertThat(found).isEmpty();
    }

    @Test
    void testSaveAndFind() throws SQLException {
        Url url = new Url("https://example.com");
        UrlRepository.save(url);
        assertThat(url.getId()).isNotNull();

        Optional<Url> byName = UrlRepository.findByName(url.getName());
        assertThat(byName).isPresent()
            .get().matches(u -> u.getName().equals(url.getName()));

        Optional<Url> byId = UrlRepository.findById(url.getId());
        assertThat(byId).isPresent()
            .get().matches(u -> u.getId().equals(url.getId()));

        List<Url> urls = UrlRepository.getEntities();
        assertThat(urls).hasSize(1)
            .first().matches(u -> u.getName().equals("https://example.com"));
    }
}
