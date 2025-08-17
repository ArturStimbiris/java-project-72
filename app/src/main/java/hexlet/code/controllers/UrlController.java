package hexlet.code.controllers;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.service.UrlChecker;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

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
            java.net.URI uri = new java.net.URI(input);
            java.net.URL parsed = uri.toURL();
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
            
            List<UrlCheck> checks = UrlCheckRepository.findByUrlId(id);
            
            Map<String, Object> model = new HashMap<>();
            model.put("flash", ctx.attribute("flash"));
            model.put("url", url);
            model.put("checks", checks);
            ctx.render("urls/show.jte", model);
        } catch (SQLException e) {
            LOGGER.error("Database error when fetching URL by ID: {}", id, e);
            ctx.status(500).result("Ошибка при доступе к базе данных");
        }
    }

    public static void check(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        LOGGER.info("Checking URL by ID: {}", id);
        
        try {
            var url = UrlRepository.findById(id)
                    .orElseThrow(() -> new NotFoundResponse("URL не найден"));
            
            UrlCheck check = UrlChecker.check(url);
            
            if (check != null) {
                UrlCheckRepository.save(check);
                ctx.sessionAttribute("flash", "Страница успешно проверена");
            } else {
                ctx.sessionAttribute("flash", "Ошибка при проверке страницы");
            }
        } catch (Exception e) {
            LOGGER.error("Error during URL check", e);
            ctx.sessionAttribute("flash", "Ошибка при проверке: " + e.getMessage());
        }
        
        ctx.redirect(NamedRoutes.urlPath(id));
    }
}
