package hexlet.code.dto;

import hexlet.code.model.UrlCheck;
import java.time.LocalDateTime;

public final class UrlDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private UrlCheck lastCheck;

    public UrlDto(Long id, String name, LocalDateTime createdAt, UrlCheck lastCheck) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.lastCheck = lastCheck;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UrlCheck getLastCheck() {
        return lastCheck;
    }
}
