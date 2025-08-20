package hexlet.code.service;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UrlChecker {

    public static UrlCheck check(Url url) throws UnirestException {
        HttpResponse<String> response = Unirest
                .get(url.getName())
                .asString();

        int statusCode = response.getStatus();
        Document doc = Jsoup.parse(response.getBody());

        String title = doc.title();
        String h1 = doc.selectFirst("h1") != null ? doc.selectFirst("h1").text() : "";
        String description = doc.selectFirst("meta[name=description]") != null
                ? doc.selectFirst("meta[name=description]").attr("content")
                : "";

        UrlCheck urlCheck = new UrlCheck();
        urlCheck.setUrlId(url.getId());
        urlCheck.setStatusCode(statusCode);
        urlCheck.setTitle(title);
        urlCheck.setH1(h1);
        urlCheck.setDescription(description);

        return urlCheck;
    }
}
