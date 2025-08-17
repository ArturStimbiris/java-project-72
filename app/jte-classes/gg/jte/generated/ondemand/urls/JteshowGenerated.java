package gg.jte.generated.ondemand.urls;
import hexlet.code.util.NamedRoutes;
import hexlet.code.model.Url;
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,16,16,16,17,17,17,18,18,23,23,23,26,26,26,29,29,29,32,32,32,32,32,32,32,32,32,36,36,36,3,4,4,4,4};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String flash, Url url) {
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
		jteOutput.writeContent("</dd>\n    </dl>\n\n    <a");
		var __jte_html_attribute_0 = NamedRoutes.urlsPath();
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
			jteOutput.writeContent(" href=\"");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(__jte_html_attribute_0);
			jteOutput.setContext("a", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(" class=\"btn btn-secondary\">Назад к списку</a>\n\n  </div>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String flash = (String)params.get("flash");
		Url url = (Url)params.get("url");
		render(jteOutput, jteHtmlInterceptor, flash, url);
	}
}
