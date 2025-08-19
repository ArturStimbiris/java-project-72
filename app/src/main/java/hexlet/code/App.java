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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

public class App {

    static {
        // Конфигурация Unirest
        Unirest.config()
            .socketTimeout(5000)
            .connectTimeout(5000)
            .concurrency(10, 5)
            .setDefaultHeader("Accept", "text/html")
            .setDefaultHeader("User-Agent", "Mozilla/5.0 (compatible; MyApp/1.0)");
    }

    private static boolean isProduction() {
        return System.getenv().getOrDefault("APP_ENV", "dev").equals("prod");
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    public static Javalin getApp() {
        try {
            String defaultJdbcUrl = "jdbc:h2:mem:project" + System.currentTimeMillis() + ";DB_CLOSE_DELAY=-1;";
            String jdbcUrl = System.getenv().getOrDefault(
                "JDBC_DATABASE_URL",
                defaultJdbcUrl
            );

            System.out.println("Using database URL: " + jdbcUrl);

            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(jdbcUrl);

            if (jdbcUrl.startsWith("jdbc:h2")) {
                hikariConfig.setUsername("");
                hikariConfig.setPassword("");
            } else if (jdbcUrl.startsWith("jdbc:postgresql")) {
                hikariConfig.setMaximumPoolSize(5);
                hikariConfig.setConnectionInitSql("SELECT 1");
            }

            HikariDataSource dataSource = new HikariDataSource(hikariConfig);
            BaseRepository.setDataSource(dataSource);

            try (var conn = dataSource.getConnection();
                 var stmt = conn.createStatement()) {
                System.out.println("Database connection established");

                InputStream inputStream = App.class.getClassLoader().getResourceAsStream("schema.sql");
                if (inputStream != null) {
                    String sql = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));

                    System.out.println("Executing SQL schema:\n" + sql);
                    stmt.execute(sql);
                    System.out.println("Database schema initialized");
                } else {
                    System.out.println("Schema file not found");
                }
            } catch (Exception e) {
                System.err.println("Database initialization failed:");
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            Javalin app = Javalin.create(config -> {
                TemplateEngine templateEngine = createTemplateEngine();
                config.fileRenderer(new JavalinJte(templateEngine));
            });

            app.before(ctx -> {
                String flash = ctx.sessionAttribute("flash");
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
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize application", e);
        }
    }

    public static void main(String[] args) {
        try {
            Javalin app = getApp();
            int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
            app.start(port);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
