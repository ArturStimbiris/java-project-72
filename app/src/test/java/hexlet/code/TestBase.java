package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TestBase {
    protected Javalin app;
    protected HikariDataSource dataSource;

    @BeforeEach
    public void beforeEach() throws Exception {
        // Создаём уникальную in-memory БД для каждого теста
        var hikariConfig = new HikariConfig();
        String jdbcUrl = "jdbc:h2:mem:test_" + System.currentTimeMillis() + ";DB_CLOSE_DELAY=-1;";
        hikariConfig.setJdbcUrl(jdbcUrl);
        dataSource = new HikariDataSource(hikariConfig);

        // Устанавливаем её в репозитории и инициализируем схему
        BaseRepository.setDataSource(dataSource);
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            String sql = Files.readString(Paths.get("src/main/resources/schema.sql"));
            stmt.execute(sql);
        }

        // Создаём приложение — оно по умолчанию создаст своё соединение, но
        // ниже мы его перезатираем тестовым
        app = App.getApp();
        // Переключаем репозитории обратно на тестовую БД
        BaseRepository.setDataSource(dataSource);
    }

    @AfterEach
    public void afterEach() {
        if (app != null) {
            app.stop();
        }
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
