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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class App {

    private static boolean isProduction() {
        return System.getenv().getOrDefault("APP_ENV", "dev").equals("prod");
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    public static Javalin getApp() throws Exception {
        // Конфигурация базы данных
        String jdbcUrl = System.getenv().getOrDefault(
            "JDBC_DATABASE_URL",
            "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;"
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

        // Инициализация базы данных
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
            throw e;
        }

        // Создание и настройка Javalin - упрощенная версия без dev logging
        Javalin app = Javalin.create(config -> {
            // Регистрация шаблонизатора JTE
            TemplateEngine templateEngine = createTemplateEngine();
            config.fileRenderer(new JavalinJte(templateEngine));
        });

        // Middleware для flash-сообщений
        app.before(ctx -> {
            String flash = ctx.sessionAttribute("flash");
            if (flash != null) {
                ctx.attribute("flash", flash);
                ctx.sessionAttribute("flash", null);
            }
        });

        // Регистрация маршрутов
        app.get(NamedRoutes.rootPath(), ctx -> ctx.render("index.jte"));
        app.post(NamedRoutes.urlsPath(), UrlController::create);
        app.get(NamedRoutes.urlsPath(), UrlController::index);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);

        return app;
    }

    public static void main(String[] args) throws Exception {
        Javalin app = getApp();
        app.start(7070);
    }
}
