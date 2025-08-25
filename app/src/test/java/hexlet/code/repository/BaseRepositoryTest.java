package hexlet.code.repository;

import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.TestBase;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseRepositoryTest extends TestBase {

    @Test
    void testSetDataSource() {
        HikariDataSource originalDataSource = BaseRepository.dataSource;

        HikariDataSource newDataSource = new HikariDataSource();
        newDataSource.setJdbcUrl("jdbc:h2:mem:test_" + System.currentTimeMillis());
        newDataSource.setUsername("");
        newDataSource.setPassword("");

        BaseRepository.setDataSource(newDataSource);
        assertThat(BaseRepository.dataSource).isEqualTo(newDataSource);

        BaseRepository.setDataSource(originalDataSource);
    }
}
