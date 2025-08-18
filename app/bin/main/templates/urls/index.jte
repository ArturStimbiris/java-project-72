@import hexlet.code.util.NamedRoutes
@import java.util.List
@import hexlet.code.model.Url

@param String flash
@param List<Url> urls

@template.layout.page(
    content = @`
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
                            <th style="width: 5%">ID</th>
                            <th style="width: 50%">URL</th>
                            <th style="width: 15%">Последняя проверка</th>
                            <th style="width: 10%">Код ответа</th>
                            <th style="width: 20%">Действия</th>
                        </tr>
                    </thead>
                    <tbody>
                        @for (Url url : urls)
                            <tr>
                                <td>${url.getId()}</td>
                                <td class="url-cell">
                                    <a href="${url.getName()}" target="_blank">${url.getName()}</a>
                                </td>
                                <td>
                                    @if(url.getLastCheck() != null)
                                        ${url.getLastCheck().getFormattedCreatedAt()}
                                    @endif
                                </td>
                                <td>
                                    @if(url.getLastCheck() != null)
                                        ${url.getLastCheck().getStatusCode()}
                                    @endif
                                </td>
                                <td>
                                    <a href="${NamedRoutes.urlPath(url.getId())}" class="btn btn-sm btn-primary">
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
    `
)