@import hexlet.code.util.NamedRoutes
@import hexlet.code.model.Url
@import java.util.List

@param String flash
@param List<Url> urls

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список URL</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .url-table {
            table-layout: fixed;
        }
        .url-cell {
            word-wrap: break-word;
            max-width: 500px;
        }
        .container-custom {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
    </style>
</head>
<body class="bg-light">
  <div class="container-custom py-5">

    @if(flash != null)
      <div class="alert alert-info alert-dismissible fade show" role="alert">
        ${flash}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
      </div>
    @endif

    <h1 class="mb-4">Список добавленных URL</h1>
    
    @if(urls == null || urls.isEmpty())
      <div class="alert alert-warning">
        Нет сохранённых URL. <a href="${NamedRoutes.rootPath()}">Добавить URL</a>
      </div>
    @else
      <div class="table-responsive">
        <table class="table table-striped url-table">
          <thead class="table-dark">
            <tr>
              <th style="width: 10%">ID</th>
              <th style="width: 60%">URL</th>
              <th style="width: 20%">Дата добавления</th>
              <th style="width: 10%">Действия</th>
            </tr>
          </thead>
          <tbody>
            @for (Url url : urls)
              <tr>
                <td>${url.getId()}</td>
                <td class="url-cell">
                  <a href="${url.getName()}" target="_blank">${url.getName()}</a>
                </td>
                <td>${url.getFormattedCreatedAt()}</td>
                <td>
                  <a href="${NamedRoutes.urlPath(url.getId())}" 
                     class="btn btn-sm btn-primary">
                    Подробнее
                  </a>
                </td>
              </tr>
            @endfor
          </tbody>
        </table>
      </div>
    @endif

    <div class="mt-4">
      <a href="${NamedRoutes.rootPath()}" class="btn btn-secondary">На главную</a>
    </div>

  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>