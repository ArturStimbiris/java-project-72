package hexlet.code;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import hexlet.code.controllers.RootController;
import hexlet.code.controllers.UrlController;
import hexlet.code.model.Url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Collections;
import java.util.List;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    private static HikariDataSource configureDataSource() {
        String jdbcUrl = System.getenv().getOrDefault("JDBC_DATABASE_URL",
            "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        return new HikariDataSource(config);
    }

    private static void initDatabase(HikariDataSource dataSource) throws Exception {
        var sql = """
            CREATE TABLE IF NOT EXISTS urls (
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(255) NOT NULL UNIQUE,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static TemplateEngine createTemplateEngine() {
        return TemplateEngine.create(
            new ResourceCodeResolver("templates", App.class.getClassLoader()),
            ContentType.Html
        );
    }

    public static Javalin getApp() throws Exception {
        var dataSource = configureDataSource();
        RootController.setDataSource(dataSource);
        initDatabase(dataSource);

        Javalin app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.before(ctx -> {
            LOGGER.info("Received request: {} {}", ctx.method(), ctx.path());
        });

        app.get("/", ctx -> {
            List<Url> urls = UrlController.getAll();
            ctx.render("index.jte", Collections.singletonMap("urls", urls));
        });

        return app;
    }

    public static void main(String[] args) throws Exception {
        Javalin app = getApp();
        app.start(getPort());
    }
}
