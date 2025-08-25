package hexlet.code.dto;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class UrlInfoDtoTest {

    @Test
    void testUrlInfoDto() {
        Url url = new Url("https://example.com");
        url.setId(1L);
        url.setCreatedAt(LocalDateTime.now());

        UrlCheck check = new UrlCheck();
        check.setId(1L);
        check.setStatusCode(200);

        UrlInfoDto dto = new UrlInfoDto(url, List.of(check));

        assertThat(dto.getUrl()).isEqualTo(url);
        assertThat(dto.getChecks()).hasSize(1);
        assertThat(dto.getChecks().get(0)).isEqualTo(check);
    }

    @Test
    void testUrlInfoDtoWithEmptyChecks() {
        Url url = new Url("https://example.com");
        url.setId(1L);
        url.setCreatedAt(LocalDateTime.now());

        UrlInfoDto dto = new UrlInfoDto(url, List.of());

        assertThat(dto.getUrl()).isEqualTo(url);
        assertThat(dto.getChecks()).isEmpty();
    }
}
