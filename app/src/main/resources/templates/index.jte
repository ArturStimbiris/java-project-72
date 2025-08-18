@import hexlet.code.util.NamedRoutes
@param String flash

@template.layout.page(
    content = @`
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
    `
)