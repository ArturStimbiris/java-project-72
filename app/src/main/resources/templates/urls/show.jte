@import hexlet.code.util.NamedRoutes
@import hexlet.code.model.Url

@param String flash
@param Url url

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
      <dd class="col-sm-9">${url.getCreatedAt()}</dd>
    </dl>

    <a href="${NamedRoutes.urlsPath()}" class="btn btn-secondary">Назад к списку</a>

  </div>
</body>
</html>