package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TestBase {
    protected Javalin app;
    protected HikariDataSource dataSource;

    /**
     * Sets up the test environment before each test method execution.
     * Initializes in-memory H2 database, loads schema, and starts Javalin app.
     *
     * @throws Exception if any error occurs during setup
     */
    @BeforeEach
    public void beforeEach() throws Exception {
        var hikariConfig = new HikariConfig();
        String jdbcUrl = "jdbc:h2:mem:test_" + System.currentTimeMillis() + ";DB_CLOSE_DELAY=-1;";
        hikariConfig.setJdbcUrl(jdbcUrl);
        dataSource = new HikariDataSource(hikariConfig);

        BaseRepository.setDataSource(dataSource);
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            String sql = Files.readString(Paths.get("src/main/resources/schema.sql"));
            stmt.execute(sql);
        }

        app = App.getApp();
        BaseRepository.setDataSource(dataSource);
    }

    /**
     * Cleans up resources after each test method execution.
     * Stops Javalin app and closes database connection.
     */
    @AfterEach
    public void afterEach() {
        if (app != null) {
            app.stop();
        }
        if (dataSource != null) {
            dataSource.close();
        }
    }

    @AfterAll
    public static void afterAll() {
        Unirest.shutDown();
    }
}
