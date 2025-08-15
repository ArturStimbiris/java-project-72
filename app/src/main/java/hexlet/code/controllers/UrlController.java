package hexlet.code.controllers;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.List; // Добавлен импорт List

public class UrlController {

    public static void create(Context ctx) {
        String input = ctx.formParam("url");
        if (input == null || input.isEmpty()) {
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

            var existing = UrlRepository.findByName(normalized);
            if (existing.isPresent()) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.redirect(NamedRoutes.urlPath(existing.get().getId()));
                return;
            }

            Url newUrl = new Url(normalized);
            UrlRepository.save(newUrl);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void index(Context ctx) {
        try {
            List<Url> urls = UrlRepository.getEntities();
            ctx.attribute("urls", urls);
            ctx.render("urls/index.jte");
        } catch (SQLException e) {
            ctx.status(500).result("Ошибка при доступе к базе данных");
        }
    }

    public static void show(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        try {
            var url = UrlRepository.findById(id)
                    .orElseThrow(() -> new NotFoundResponse("URL не найден"));
            ctx.attribute("url", url);
            ctx.render("urls/show.jte");
        } catch (SQLException e) {
            ctx.status(500).result("Ошибка при доступе к базе данных");
        }
    }
}
