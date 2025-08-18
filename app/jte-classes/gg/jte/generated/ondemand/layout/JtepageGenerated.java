package gg.jte.generated.ondemand.layout;
import gg.jte.Content;
import hexlet.code.util.NamedRoutes;
public final class JtepageGenerated {
	public static final String JTE_NAME = "layout/page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,16,16,16,16,16,16,16,16,16,16,21,21,21,21,21,21,21,21,21,22,22,22,22,22,22,22,22,22,28,28,28,32,32,33,33,33,34,34,36,36,41,41,41,2,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content, Content footer) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html lang=\"ru\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>Анализатор страниц</title>\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n</head>\n<body class=\"bg-light\">\n    <header class=\"p-3 bg-dark text-white\">\n        <div class=\"container d-flex flex-column flex-md-row align-items-center justify-content-between\">\n            <h1 class=\"h3 mb-2 mb-md-0\">\n                <a");
		var __jte_html_attribute_0 = NamedRoutes.rootPath();
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
			jteOutput.writeContent(" href=\"");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(__jte_html_attribute_0);
			jteOutput.setContext("a", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(" class=\"text-white text-decoration-none\">\n                    Анализатор страниц\n                </a>\n            </h1>\n            <nav>\n                <a");
		var __jte_html_attribute_1 = NamedRoutes.rootPath();
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
			jteOutput.writeContent(" href=\"");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(__jte_html_attribute_1);
			jteOutput.setContext("a", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(" class=\"text-white me-3\">Главная</a>\n                <a");
		var __jte_html_attribute_2 = NamedRoutes.urlsPath();
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_2)) {
			jteOutput.writeContent(" href=\"");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(__jte_html_attribute_2);
			jteOutput.setContext("a", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(" class=\"text-white\">Сайты</a>\n            </nav>\n        </div>\n    </header>\n\n    <main class=\"container py-5\">\n        ");
		jteOutput.setContext("main", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\n    </main>\n\n    <footer class=\"text-center py-3 bg-light\">\n        ");
		if (footer != null) {
			jteOutput.writeContent("\n            ");
			jteOutput.setContext("footer", null);
			jteOutput.writeUserContent(footer);
			jteOutput.writeContent("\n        ");
		} else {
			jteOutput.writeContent("\n            <p>© 2023 Анализатор страниц</p>\n        ");
		}
		jteOutput.writeContent("\n    </footer>\n\n    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js\"></script>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.get("content");
		Content footer = (Content)params.getOrDefault("footer", null);
		render(jteOutput, jteHtmlInterceptor, content, footer);
	}
}
