package hexlet.code.repository;

import hexlet.code.TestBase;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlCheckRepositoryTest extends TestBase {

    private Url url1;
    private Url url2;

    @BeforeEach
    void initUrls() throws SQLException {
        url1 = new Url("https://a.example.com");
        url2 = new Url("https://b.example.com");
        UrlRepository.save(url1);
        UrlRepository.save(url2);
    }

    @Test
    void testEmptyFindByUrlId() throws SQLException {
        List<UrlCheck> checks = UrlCheckRepository.findByUrlId(url1.getId());
        assertThat(checks).isEmpty();
    }

    @Test
    void testSaveAndFindByUrlId() throws SQLException {
        UrlCheck check = new UrlCheck();
        check.setUrlId(url1.getId());
        check.setStatusCode(200);
        UrlCheckRepository.save(check);

        List<UrlCheck> checks = UrlCheckRepository.findByUrlId(url1.getId());
        assertThat(checks).hasSize(1)
            .first().matches(c ->
                c.getStatusCode() == 200
                && c.getUrlId().equals(url1.getId())
        );
    }

    @Test
    void testFindLastCheckByUrlId() throws SQLException {
        UrlCheck first = new UrlCheck();
        first.setUrlId(url1.getId());
        first.setStatusCode(200);
        UrlCheckRepository.save(first);

        UrlCheck second = new UrlCheck();
        second.setUrlId(url1.getId());
        second.setStatusCode(404);
        UrlCheckRepository.save(second);

        UrlCheck last = UrlCheckRepository.findLastCheckByUrlId(url1.getId());
        assertThat(last).isNotNull();
        assertThat(last.getStatusCode()).isEqualTo(404);
    }

    @Test
    void testFindLatestChecksMap() throws SQLException {
        UrlCheck c1 = new UrlCheck();
        c1.setUrlId(url1.getId());
        c1.setStatusCode(200);
        UrlCheckRepository.save(c1);

        UrlCheck c2 = new UrlCheck();
        c2.setUrlId(url2.getId());
        c2.setStatusCode(301);
        UrlCheckRepository.save(c2);

        UrlCheck c3 = new UrlCheck();
        c3.setUrlId(url1.getId());
        c3.setStatusCode(500);
        UrlCheckRepository.save(c3);

        Map<Long, UrlCheck> latest = UrlCheckRepository.findLatestChecks();
        assertThat(latest).hasSize(2);
        assertThat(latest.get(url1.getId()).getStatusCode()).isEqualTo(500);
        assertThat(latest.get(url2.getId()).getStatusCode()).isEqualTo(301);
    }
}
