package hexlet.code.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class UrlTest {

    @Test
    void testUrl() {
        Url url = new Url();
        url.setId(1L);
        url.setName("https://example.com");
        LocalDateTime now = LocalDateTime.now();
        url.setCreatedAt(now);

        assertThat(url.getId()).isEqualTo(1L);
        assertThat(url.getName()).isEqualTo("https://example.com");
        assertThat(url.getCreatedAt()).isEqualTo(now);
        assertThat(url.getFormattedCreatedAt()).isNotEmpty();
    }

    @Test
    void testUrlConstructor() {
        Url url = new Url("https://example.com");
        assertThat(url.getName()).isEqualTo("https://example.com");
    }
}
