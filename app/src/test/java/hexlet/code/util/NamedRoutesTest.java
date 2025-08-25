package hexlet.code.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NamedRoutesTest {

    @Test
    void testRootPath() {
        assertThat(NamedRoutes.rootPath()).isEqualTo("/");
    }

    @Test
    void testUrlsPath() {
        assertThat(NamedRoutes.urlsPath()).isEqualTo("/urls");
    }

    @Test
    void testUrlPathWithLong() {
        Long id = 1L;
        assertThat(NamedRoutes.urlPath(id)).isEqualTo("/urls/1");
    }

    @Test
    void testUrlPathWithString() {
        String id = "test";
        assertThat(NamedRoutes.urlPath(id)).isEqualTo("/urls/test");
    }

    @Test
    void testCheckPathWithLong() {
        Long id = 1L;
        assertThat(NamedRoutes.checkPath(id)).isEqualTo("/urls/1/checks");
    }

    @Test
    void testCheckPathWithString() {
        String id = "test";
        assertThat(NamedRoutes.checkPath(id)).isEqualTo("/urls/test/checks");
    }
}
