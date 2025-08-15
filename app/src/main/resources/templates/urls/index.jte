@import hexlet.code.util.NamedRoutes
@import hexlet.code.model.Url

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список URL</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
  <div class="container py-5">

    @if(flash != null)
      <div class="alert alert-info">${flash}</div>
    @endif

    <h1 class="mb-4">Все сохранённые URL</h1>
    <table class="table table-striped">
      <thead>
        <tr>
          <th>ID</th>
          <th>Адрес</th>
          <th>Дата создания</th>
          <th>Действия</th>
        </tr>
      </thead>
      <tbody>
        @for (Url url : urls)
          <tr>
            <td>${url.getId()}</td>
            <td>${url.getAddress()}</td>
            <td>${url.getCreatedAt()}</td>
            <td>
              <a href="${NamedRoutes.urlPath(url.getId())}" class="btn btn-sm btn-info">
                Подробнее
              </a>
            </td>
          </tr>
        @endfor
      </tbody>
    </table>

  </div>
</body>
</html>