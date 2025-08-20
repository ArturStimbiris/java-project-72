package hexlet.code.dto;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import java.util.List;

public final class UrlInfoDto {
    private Url url;
    private List<UrlCheck> checks;

    public UrlInfoDto(Url url, List<UrlCheck> checks) {
        this.url = url;
        this.checks = checks;
    }

    public Url getUrl() {
        return url;
    }

    public List<UrlCheck> getChecks() {
        return checks;
    }
}
