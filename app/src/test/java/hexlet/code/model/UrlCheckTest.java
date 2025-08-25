package hexlet.code.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class UrlCheckTest {

    @Test
    void testUrlCheck() {
        UrlCheck check = new UrlCheck();
        check.setId(1L);
        check.setUrlId(1L);
        check.setStatusCode(200);
        check.setTitle("Test Title");
        check.setH1("Test H1");
        check.setDescription("Test Description");
        LocalDateTime now = LocalDateTime.now();
        check.setCreatedAt(now);

        assertThat(check.getId()).isEqualTo(1L);
        assertThat(check.getUrlId()).isEqualTo(1L);
        assertThat(check.getStatusCode()).isEqualTo(200);
        assertThat(check.getTitle()).isEqualTo("Test Title");
        assertThat(check.getH1()).isEqualTo("Test H1");
        assertThat(check.getDescription()).isEqualTo("Test Description");
        assertThat(check.getCreatedAt()).isEqualTo(now);
        assertThat(check.getFormattedCreatedAt()).isNotEmpty();
    }
}
