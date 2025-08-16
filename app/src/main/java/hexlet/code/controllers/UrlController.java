package hexlet.code.controllers;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class);

    public static void create(Context ctx) {
        String input = ctx.formParam("url");
        LOGGER.info("Received URL input: {}", input);
        
        if (input == null || input.isEmpty()) {
            LOGGER.warn("Empty URL submitted");
            ctx.sessionAttribute("flash", "URL не может быть пустым");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }

        try {
            URI uri = new URI(input);
            URL parsed = uri.toURL();
            int port = parsed.getPort();
            String normalized = parsed.getProtocol() + "://"
                    + parsed.getHost()
                    + (port == -1 ? "" : ":" + port);

            LOGGER.info("Normalized URL: {}", normalized);

            var existing = UrlRepository.findByName(normalized);
            if (existing.isPresent()) {
                LOGGER.info("URL already exists: {}", normalized);
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.redirect(NamedRoutes.urlPath(existing.get().getId()));
                return;
            }

            LOGGER.info("Saving new URL: {}", normalized);
            Url newUrl = new Url(normalized);
            UrlRepository.save(newUrl);
            LOGGER.info("URL saved successfully, ID: {}", newUrl.getId());
            
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (Exception e) {
            LOGGER.error("Invalid URL: {}", input, e);
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void index(Context ctx) {
        try {
            LOGGER.info("Fetching all URLs");
            List<Url> urls = UrlRepository.getEntities();
            
            // Добавим логирование содержимого
            if (urls.isEmpty()) {
                LOGGER.info("URL list is empty");
            } else {
                for (Url url : urls) {
                    LOGGER.info("URL in list: ID={}, Name={}", url.getId(), url.getName());
                }
            }
            
            Map<String, Object> model = new HashMap<>();
            model.put("flash", ctx.attribute("flash"));
            model.put("urls", urls);
            ctx.render("urls/index.jte", model);
        } catch (SQLException e) {
            LOGGER.error("Database error when fetching URLs", e);
            ctx.status(500).result("Ошибка при доступе к базе данных");
        }
    }

    public static void show(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        LOGGER.info("Fetching URL by ID: {}", id);
        
        try {
            var url = UrlRepository.findById(id)
                    .orElseThrow(() -> new NotFoundResponse("URL не найден"));
            
            Map<String, Object> model = new HashMap<>();
            model.put("flash", ctx.attribute("flash"));
            model.put("url", url);
            ctx.render("urls/show.jte", model);
        } catch (SQLException e) {
            LOGGER.error("Database error when fetching URL by ID: {}", id, e);
            ctx.status(500).result("Ошибка при доступе к базе данных");
        }
    }
}
