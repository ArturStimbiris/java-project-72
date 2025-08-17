package gg.jte.generated.ondemand.urls;
import hexlet.code.util.NamedRoutes;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import java.util.List;
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,5,5,5,19,19,19,20,20,20,21,21,26,26,26,29,29,29,32,32,32,35,35,35,35,35,35,35,35,35,52,52,53,53,55,55,55,56,56,56,57,57,57,58,58,58,59,59,59,60,60,60,62,62,63,63,67,67,71,71,71,71,71,71,71,71,71,75,75,75,5,6,7,7,7,7};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String flash, Url url, List<UrlCheck> checks) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html lang=\"ru\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>Детали URL</title>\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n</head>\n<body class=\"bg-light\">\n  <div class=\"container py-5\">\n\n    ");
		if (flash != null) {
			jteOutput.writeContent("\n      <div class=\"alert alert-info\">");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(flash);
			jteOutput.writeContent("</div>\n    ");
		}
		jteOutput.writeContent("\n\n    <h1 class=\"mb-4\">Детали URL</h1>\n    <dl class=\"row\">\n      <dt class=\"col-sm-3\">ID</dt>\n      <dd class=\"col-sm-9\">");
		jteOutput.setContext("dd", null);
		jteOutput.writeUserContent(url.getId());
		jteOutput.writeContent("</dd>\n\n      <dt class=\"col-sm-3\">Адрес</dt>\n      <dd class=\"col-sm-9\">");
		jteOutput.setContext("dd", null);
		jteOutput.writeUserContent(url.getAddress());
		jteOutput.writeContent("</dd>\n\n      <dt class=\"col-sm-3\">Дата создания</dt>\n      <dd class=\"col-sm-9\">");
		jteOutput.setContext("dd", null);
		jteOutput.writeUserContent(url.getFormattedCreatedAt());
		jteOutput.writeContent("</dd>\n    </dl>\n\n    <form");
		var __jte_html_attribute_0 = NamedRoutes.checkPath(url.getId());
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
			jteOutput.writeContent(" action=\"");
			jteOutput.setContext("form", "action");
			jteOutput.writeUserContent(__jte_html_attribute_0);
			jteOutput.setContext("form", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(" method=\"post\" class=\"mb-3\">\n      <button type=\"submit\" class=\"btn btn-primary\">Проверить</button>\n    </form>\n\n    <h2 class=\"mt-4\">Проверки</h2>\n    <table class=\"table table-striped\">\n      <thead>\n        <tr>\n          <th>ID</th>\n          <th>Код ответа</th>\n          <th>Заголовок</th>\n          <th>H1</th>\n          <th>Описание</th>\n          <th>Дата</th>\n        </tr>\n      </thead>\n      <tbody>\n        ");
		if (checks != null && !checks.isEmpty()) {
			jteOutput.writeContent("\n          ");
			for (UrlCheck check : checks) {
				jteOutput.writeContent("\n            <tr>\n              <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getId());
				jteOutput.writeContent("</td>\n              <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getStatusCode());
				jteOutput.writeContent("</td>\n              <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getTitle());
				jteOutput.writeContent("</td>\n              <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getH1());
				jteOutput.writeContent("</td>\n              <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getDescription());
				jteOutput.writeContent("</td>\n              <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getFormattedCreatedAt());
				jteOutput.writeContent("</td>\n            </tr>\n          ");
			}
			jteOutput.writeContent("\n        ");
		} else {
			jteOutput.writeContent("\n          <tr>\n            <td colspan=\"6\">Проверок еще не было</td>\n          </tr>\n        ");
		}
		jteOutput.writeContent("\n      </tbody>\n    </table>\n\n    <a");
		var __jte_html_attribute_1 = NamedRoutes.urlsPath();
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
			jteOutput.writeContent(" href=\"");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(__jte_html_attribute_1);
			jteOutput.setContext("a", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(" class=\"btn btn-secondary\">Назад к списку</a>\n\n  </div>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String flash = (String)params.get("flash");
		Url url = (Url)params.get("url");
		List<UrlCheck> checks = (List<UrlCheck>)params.get("checks");
		render(jteOutput, jteHtmlInterceptor, flash, url, checks);
	}
}
