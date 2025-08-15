@import hexlet.code.util.NamedRoutes

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Добавить URL</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
  <div class="container py-5">

    @if(flash != null)
      <div class="alert alert-info">${flash}</div>
    @endif

    <h1 class="mb-4">Добавить новый URL</h1>
    <form action="${NamedRoutes.urlsPath()}" method="post" class="mb-5">
      <div class="input-group">
        <input
          type="url"
          name="url"
          class="form-control"
          placeholder="https://example.com"
          required
        >
        <button class="btn btn-primary" type="submit">Добавить</button>
      </div>
    </form>

  </div>
</body>
</html>