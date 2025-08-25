package hexlet.code.controllers;

import hexlet.code.dto.UrlDto;
import hexlet.code.dto.UrlInfoDto;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.service.UrlChecker;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.UnirestException;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class UrlController {

    public static void create(Context ctx) {
        String input = ctx.formParam("url");
        log.info("Received URL input: {}", input);

        if (input == null || input.isEmpty()) {
            log.warn("Empty URL submitted");
            ctx.sessionAttribute("flash", "URL не может быть пустым");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }

        URL parsedUrl;
        try {
            URI uri = new URI(input);
            parsedUrl = uri.toURL();
        } catch (Exception e) {
            log.error("Invalid URL: {}", input, e);
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }

        int port = parsedUrl.getPort();
        String normalized = parsedUrl.getProtocol() + "://"
                + parsedUrl.getHost()
                + (port == -1 ? "" : ":" + port);

        log.info("Normalized URL: {}", normalized);

        try {
            var existing = UrlRepository.findByName(normalized);
            if (existing.isPresent()) {
                log.info("URL already exists: {}", normalized);
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.redirect(NamedRoutes.urlPath(existing.get().getId()));
                return;
            }

            log.info("Saving new URL: {}", normalized);
            Url newUrl = new Url(normalized);
            UrlRepository.save(newUrl);
            log.info("URL saved successfully, ID: {}", newUrl.getId());

            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (Exception e) {
            log.error("Error when saving URL", e);
            ctx.sessionAttribute("flash", "Ошибка при сохранении URL");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void index(Context ctx) throws SQLException {
        log.info("Fetching all URLs");
        List<Url> urls = UrlRepository.getEntities();

        List<UrlDto> urlDtos = urls.stream()
            .map(url -> {
                try {
                    UrlCheck lastCheck = UrlCheckRepository.findLastCheckByUrlId(url.getId());
                    return new UrlDto(
                        url.getId(),
                        url.getName(),
                        url.getCreatedAt(),
                        lastCheck
                    );
                } catch (SQLException e) {
                    log.error("Error fetching last check for URL ID: {}", url.getId(), e);
                    return new UrlDto(
                        url.getId(),
                        url.getName(),
                        url.getCreatedAt(),
                        null
                    );
                }
            })
            .collect(Collectors.toList());

        Map<String, Object> model = new HashMap<>();
        model.put("flash", ctx.attribute("flash"));
        model.put("urls", urlDtos);
        ctx.render("urls/index.jte", model);
    }

    public static void show(Context ctx) throws SQLException {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        log.info("Fetching URL by ID: {}", id);

        var url = UrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("URL не найден"));

        List<UrlCheck> checks = UrlCheckRepository.findByUrlId(id);
        UrlInfoDto urlInfo = new UrlInfoDto(url, checks);

        Map<String, Object> model = new HashMap<>();
        model.put("flash", ctx.attribute("flash"));
        model.put("urlInfo", urlInfo);
        ctx.render("urls/show.jte", model);
    }

    public static void check(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        log.info("Checking URL by ID: {}", id);

        try {
            var url = UrlRepository.findById(id)
                    .orElseThrow(() -> new NotFoundResponse("URL не найден"));

            UrlCheck check = UrlChecker.check(url);
            UrlCheckRepository.save(check);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
        } catch (UnirestException e) {
            log.error("Network error during URL check", e);
            ctx.sessionAttribute("flash", "Ошибка сети: невозможно подключиться к сайту");
        } catch (Exception e) {
            log.error("Error during URL check", e);
            ctx.sessionAttribute("flash", "Ошибка при проверке: " + e.getMessage());
        }

        ctx.redirect(NamedRoutes.urlPath(id));
    }
}
