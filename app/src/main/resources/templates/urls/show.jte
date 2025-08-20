@import hexlet.code.util.NamedRoutes
@import java.util.List
@import hexlet.code.model.UrlCheck
@import hexlet.code.dto.UrlInfoDto

@param String flash
@param UrlInfoDto urlInfo

@template.layout.page(
    content = @`
        @if(flash != null)
            <div class="alert alert-info">${flash}</div>
        @endif

        <h1 class="mb-4">Детали URL</h1>
        <dl class="row">
            <dt class="col-sm-3">ID</dt>
            <dd class="col-sm-9">${urlInfo.getUrl().getId()}</dd>

            <dt class="col-sm-3">Адрес</dt>
            <dd class="col-sm-9">${urlInfo.getUrl().getName()}</dd>

            <dt class="col-sm-3">Дата создания</dt>
            <dd class="col-sm-9">${urlInfo.getUrl().getFormattedCreatedAt()}</dd>
        </dl>

        <form action="${NamedRoutes.checkPath(urlInfo.getUrl().getId())}" method="post" class="mb-3">
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
                @if(urlInfo.getChecks() != null && !urlInfo.getChecks().isEmpty())
                    @for(UrlCheck check : urlInfo.getChecks())
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
    `
)