package hexlet.code.controllers;

import hexlet.code.TestBase;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.HttpStatus;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlCreateTest extends TestBase {

    @Test
    void testCreateValidUrl() throws SQLException, IOException {
        JavalinTest.test(app, (server, client) -> {
            // Отправляем POST с валидным URL
            String form = "url=https://example.com";
            Response response = client.post("/urls", form);

            // После редиректа клиент возвращает список (200)
            assertThat(response.code())
                .as("Код ответа после удачного создания должен быть 200")
                .isEqualTo(HttpStatus.OK.getCode());

            String body = response.body().string();

            // Проверяем, что это страница списка и адрес на ней есть
            assertThat(body)
                .as("Страница списка должна содержать заголовок")
                .contains("Список добавленных URL");
            assertThat(body)
                .as("Страница списка должна содержать добавленный адрес")
                .contains("https://example.com");

            // И в БД ровно одна запись
            List<Url> urls = UrlRepository.getEntities();
            assertThat(urls)
                .as("В БД должна появиться одна ссылка")
                .hasSize(1);
            assertThat(urls.get(0).getName())
                .as("Сохранённая ссылка должна содержать example.com")
                .contains("example.com");
        });
    }

    @Test
    void testCreateInvalidUrl() throws SQLException, IOException {
        JavalinTest.test(app, (server, client) -> {
            // Отправляем POST с некорректным URL
            String form = "url=invalid";
            Response response = client.post("/urls", form);

            // Клиент автоматически следует за редиректом на "/", получаем код 200
            assertThat(response.code())
                .as("Код ответа после ошибки должен быть 200")
                .isEqualTo(HttpStatus.OK.getCode());

            String body = response.body().string();

            // Проверяем, что мы снова на форме добавления
            assertThat(body)
                .as("После ошибки должен быть рендер index.jte")
                .contains("<h1 class=\"mb-4\">Добавить новый URL</h1>");

            // В БД не появилось новых записей
            List<Url> urls = UrlRepository.getEntities();
            assertThat(urls)
                .as("При ошибке валидации записи не добавляются")
                .isEmpty();
        });
    }
}
