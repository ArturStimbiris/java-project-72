@import hexlet.code.util.NamedRoutes
@import hexlet.code.model.Url
@import hexlet.code.model.UrlCheck
@import java.util.List

@param String flash
@param Url url
@param List<UrlCheck> checks

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Детали URL</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
  <div class="container py-5">

    @if(flash != null)
      <div class="alert alert-info">${flash}</div>
    @endif

    <h1 class="mb-4">Детали URL</h1>
    <dl class="row">
      <dt class="col-sm-3">ID</dt>
      <dd class="col-sm-9">${url.getId()}</dd>

      <dt class="col-sm-3">Адрес</dt>
      <dd class="col-sm-9">${url.getAddress()}</dd>

      <dt class="col-sm-3">Дата создания</dt>
      <dd class="col-sm-9">${url.getFormattedCreatedAt()}</dd>
    </dl>

    <form action="${NamedRoutes.checkPath(url.getId())}" method="post" class="mb-3">
      <button type="submit" class="btn btn-primary">Проверить</button>
    </form>

    <h2 class="mt-4">Проверки</h2>
    <table class="table table-striped">
      <thead>
        <tr>
          <th>ID</th>
          <th>Код ответа</th>
          <th>Заголовок</th>
          <th>H1</th>
          <th>Описание</th>
          <th>Дата</th>
        </tr>
      </thead>
      <tbody>
        @if(checks != null && !checks.isEmpty())
          @for(UrlCheck check : checks)
            <tr>
              <td>${check.getId()}</td>
              <td>${check.getStatusCode()}</td>
              <td>${check.getTitle()}</td>
              <td>${check.getH1()}</td>
              <td>${check.getDescription()}</td>
              <td>${check.getFormattedCreatedAt()}</td>
            </tr>
          @endfor
        @else
          <tr>
            <td colspan="6">Проверок еще не было</td>
          </tr>
        @endif
      </tbody>
    </table>

    <a href="${NamedRoutes.urlsPath()}" class="btn btn-secondary">Назад к списку</a>

  </div>
</body>
</html>