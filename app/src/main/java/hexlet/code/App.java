package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controllers.UrlController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class App {

    static {
        Unirest.config()
            .socketTimeout(5000)
            .connectTimeout(5000)
            .concurrency(10, 5)
            .setDefaultHeader("Accept", "text/html")
            .setDefaultHeader("User-Agent", "Mozilla/5.0 (compatible; MyApp/1.0)");
    }

    private static TemplateEngine createTemplateEngine() {
        var classLoader = App.class.getClassLoader();
        var resolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(resolver, ContentType.Html);
    }

    public static Javalin getApp() throws SQLException {
        String defaultJdbcUrl = "jdbc:h2:mem:project" + System.currentTimeMillis() + ";DB_CLOSE_DELAY=-1;";
        String jdbcUrl = System.getenv().getOrDefault("JDBC_DATABASE_URL", defaultJdbcUrl);

        log.info("Using database URL: {}", jdbcUrl);

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        if (jdbcUrl.startsWith("jdbc:h2")) {
            hikariConfig.setUsername("");
            hikariConfig.setPassword("");
        } else if (jdbcUrl.startsWith("jdbc:postgresql")) {
            hikariConfig.setMaximumPoolSize(5);
            hikariConfig.setConnectionInitSql("SELECT 1");
        }

        var dataSource = new HikariDataSource(hikariConfig);
        BaseRepository.setDataSource(dataSource);

        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {

            log.info("Database connection established");

            InputStream in = App.class.getClassLoader().getResourceAsStream("schema.sql");
            if (in != null) {
                String sql = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

                log.info("Executing SQL schema:\n{}", sql);
                stmt.execute(sql);
                log.info("Database schema initialized");
            } else {
                log.warn("Schema file not found");
            }
        }

        var app = Javalin.create(cfg -> {
            var templateEngine = createTemplateEngine();
            cfg.fileRenderer(new JavalinJte(templateEngine));
        });

        app.exception(SQLException.class, (e, ctx) -> {
            log.error("Database error: {}", e.getMessage());
            ctx.sessionAttribute("flash", "Ошибка базы данных");
            ctx.redirect(NamedRoutes.rootPath());
        });

        app.before(ctx -> {
            var flash = ctx.sessionAttribute("flash");
            if (flash != null) {
                ctx.attribute("flash", flash);
                ctx.sessionAttribute("flash", null);
            }
        });

        app.get(NamedRoutes.rootPath(), ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("flash", ctx.attribute("flash"));
            ctx.render("index.jte", model);
        });

        app.post(NamedRoutes.urlsPath(), UrlController::create);
        app.get(NamedRoutes.urlsPath(), UrlController::index);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);
        app.post(NamedRoutes.checkPath("{id}"), UrlController::check);

        return app;
    }

    public static void main(String[] args) {
        try {
            var app = getApp();
            int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
            app.start(port);
        } catch (Exception e) {
            log.error("Application failed to start", e);
            System.exit(1);
        }
    }
}
