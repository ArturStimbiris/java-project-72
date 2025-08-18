@import gg.jte.Content
@import hexlet.code.util.NamedRoutes
@param Content content
@param Content footer = null

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Анализатор страниц</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <header class="p-3 bg-dark text-white">
        <div class="container d-flex flex-column flex-md-row align-items-center justify-content-between">
            <h1 class="h3 mb-2 mb-md-0">
                <a href="${NamedRoutes.rootPath()}" class="text-white text-decoration-none">
                    Анализатор страниц
                </a>
            </h1>
            <nav>
                <a href="${NamedRoutes.rootPath()}" class="text-white me-3">Главная</a>
                <a href="${NamedRoutes.urlsPath()}" class="text-white">Сайты</a>
            </nav>
        </div>
    </header>

    <main class="container py-5">
        ${content}
    </main>

    <footer class="text-center py-3 bg-light">
        @if(footer != null)
            ${footer}
        @else
            <p>© 2023 Анализатор страниц</p>
        @endif
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>