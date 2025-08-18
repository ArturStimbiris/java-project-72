package gg.jte.generated.ondemand.urls;
import hexlet.code.util.NamedRoutes;
import java.util.List;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,5,5,5,9,9,11,11,12,12,13,13,13,14,14,19,19,19,22,22,22,25,25,25,28,28,28,28,28,28,28,28,28,45,45,46,46,48,48,48,49,49,49,50,50,50,51,51,51,52,52,52,53,53,53,55,55,56,56,60,60,64,64,64,64,64,64,64,64,64,65,65,65,65,65,5,6,7,7,7,7};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String flash, Url url, List<UrlCheck> checks) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n        ");
				if (flash != null) {
					jteOutput.writeContent("\n            <div class=\"alert alert-info\">");
					jteOutput.setContext("div", null);
					jteOutput.writeUserContent(flash);
					jteOutput.writeContent("</div>\n        ");
				}
				jteOutput.writeContent("\n\n        <h1 class=\"mb-4\">Детали URL</h1>\n        <dl class=\"row\">\n            <dt class=\"col-sm-3\">ID</dt>\n            <dd class=\"col-sm-9\">");
				jteOutput.setContext("dd", null);
				jteOutput.writeUserContent(url.getId());
				jteOutput.writeContent("</dd>\n\n            <dt class=\"col-sm-3\">Адрес</dt>\n            <dd class=\"col-sm-9\">");
				jteOutput.setContext("dd", null);
				jteOutput.writeUserContent(url.getAddress());
				jteOutput.writeContent("</dd>\n\n            <dt class=\"col-sm-3\">Дата создания</dt>\n            <dd class=\"col-sm-9\">");
				jteOutput.setContext("dd", null);
				jteOutput.writeUserContent(url.getFormattedCreatedAt());
				jteOutput.writeContent("</dd>\n        </dl>\n\n        <form");
				var __jte_html_attribute_0 = NamedRoutes.checkPath(url.getId());
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" action=\"");
					jteOutput.setContext("form", "action");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("form", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" method=\"post\" class=\"mb-3\">\n            <button type=\"submit\" class=\"btn btn-primary\">Проверить</button>\n        </form>\n\n        <h2 class=\"mt-4\">Проверки</h2>\n        <table class=\"table table-striped\">\n            <thead>\n                <tr>\n                    <th>ID</th>\n                    <th>Код ответа</th>\n                    <th>Заголовок</th>\n                    <th>H1</th>\n                    <th>Описание</th>\n                    <th>Дата</th>\n                </tr>\n            </thead>\n            <tbody>\n                ");
				if (checks != null && !checks.isEmpty()) {
					jteOutput.writeContent("\n                    ");
					for (UrlCheck check : checks) {
						jteOutput.writeContent("\n                        <tr>\n                            <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getId());
						jteOutput.writeContent("</td>\n                            <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getStatusCode());
						jteOutput.writeContent("</td>\n                            <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getTitle());
						jteOutput.writeContent("</td>\n                            <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getH1());
						jteOutput.writeContent("</td>\n                            <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getDescription());
						jteOutput.writeContent("</td>\n                            <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getFormattedCreatedAt());
						jteOutput.writeContent("</td>\n                        </tr>\n                    ");
					}
					jteOutput.writeContent("\n                ");
				} else {
					jteOutput.writeContent("\n                    <tr>\n                        <td colspan=\"6\">Проверок еще не было</td>\n                    </tr>\n                ");
				}
				jteOutput.writeContent("\n            </tbody>\n        </table>\n\n        <a");
				var __jte_html_attribute_1 = NamedRoutes.urlsPath();
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
					jteOutput.writeContent(" href=\"");
					jteOutput.setContext("a", "href");
					jteOutput.writeUserContent(__jte_html_attribute_1);
					jteOutput.setContext("a", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" class=\"btn btn-secondary\">Назад к списку</a>\n    ");
			}
		}, null);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String flash = (String)params.get("flash");
		Url url = (Url)params.get("url");
		List<UrlCheck> checks = (List<UrlCheck>)params.get("checks");
		render(jteOutput, jteHtmlInterceptor, flash, url, checks);
	}
}
