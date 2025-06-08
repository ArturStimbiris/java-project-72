@import java.util.List
@import hexlet.code.model.Url

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>URL Analyzer</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-8 mx-auto">
                <h1 class="mb-4">URL Analyzer</h1>
                
                <form action="/urls" method="post" class="mb-5">
                    <div class="input-group">
                        <input type="text" name="url" class="form-control" placeholder="Enter URL...">
                        <button type="submit" class="btn btn-primary">Analyze</button>
                    </div>
                </form>

                @if(urls != null && !urls.isEmpty())
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>URL</th>
                                <th>Created At</th>
                            </tr>
                        </thead>
                        <tbody>
                            @for(url in urls)
                                <tr>
                                    <td>${url.id}</td>
                                    <td>${url.name}</td>
                                    <td>${url.createdAt}</td>
                                </tr>
                            @endfor
                        </tbody>
                    </table>
                @endif
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>