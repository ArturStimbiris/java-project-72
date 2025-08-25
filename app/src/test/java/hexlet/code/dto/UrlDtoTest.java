package hexlet.code.dto;

import hexlet.code.model.UrlCheck;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class UrlDtoTest {

    @Test
    void testUrlDto() {
        LocalDateTime now = LocalDateTime.now();
        UrlCheck check = new UrlCheck();
        check.setStatusCode(200);
        check.setTitle("Test Title");

        UrlDto dto = new UrlDto(1L, "https://example.com", now, check);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("https://example.com");
        assertThat(dto.getCreatedAt()).isEqualTo(now);
        assertThat(dto.getLastCheck()).isEqualTo(check);
        assertThat(dto.getLastCheck().getStatusCode()).isEqualTo(200);
        assertThat(dto.getLastCheck().getTitle()).isEqualTo("Test Title");
    }

    @Test
    void testUrlDtoWithNullCheck() {
        LocalDateTime now = LocalDateTime.now();
        UrlDto dto = new UrlDto(1L, "https://example.com", now, null);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("https://example.com");
        assertThat(dto.getCreatedAt()).isEqualTo(now);
        assertThat(dto.getLastCheck()).isNull();
    }
}
