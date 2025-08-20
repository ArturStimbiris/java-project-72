package hexlet.code.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Url {
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Url() { }

    public Url(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFormattedCreatedAt() {
        return createdAt == null ? "" : DATE_FORMAT.format(createdAt);
    }
}
