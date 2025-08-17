package hexlet.code.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Url {
    private Long id;
    private String name;
    private Timestamp createdAt;
    private UrlCheck lastCheck;
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getAddress() {
        return name;
    }
    
    public String getFormattedCreatedAt() {
        return DATE_FORMAT.format(new Date(createdAt.getTime()));
    }
    
    public UrlCheck getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(UrlCheck lastCheck) {
        this.lastCheck = lastCheck;
    }
}
