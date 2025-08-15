package gg.jte.generated.ondemand;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {13,13,13,13,13,13,13,14,14,14,15,15,27,27,27,27,27,27};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n  <meta charset=\"UTF-8\">\n  <title>URL Analyzer</title>\n  <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n</head>\n<body class=\"bg-light\">\n  <div class=\"container mt-5\">\n    <div class=\"row\">\n      <div class=\"col-md-8 mx-auto\">\n        <h1 class=\"mb-4\">URL Analyzer</h1>\n\n        ");
		if (flash) {
			jteOutput.writeContent("\n          <div class=\"alert alert-info\">");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(flash);
			jteOutput.writeContent("</div>\n        ");
		}
		jteOutput.writeContent("\n\n        <form action=\"/urls\" method=\"post\" class=\"mb-5\">\n          <div class=\"input-group\">\n            <input type=\"text\" name=\"url\" class=\"form-control\" placeholder=\"Enter URL...\">\n            <button type=\"submit\" class=\"btn btn-primary\">Analyze</button>\n          </div>\n        </form>\n      </div>\n    </div>\n  </div>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
