package hexlet.code.repository;

import hexlet.code.model.Url;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        log.debug("Executing SQL: {}", sql);

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, url.getName());
            stmt.setObject(2, LocalDateTime.now());

            int affectedRows = stmt.executeUpdate();
            log.debug("Affected rows: {}", affectedRows);

            try (var generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    url.setId(generatedKeys.getLong(1));
                    log.info("URL saved. ID: {}, Name: {}", url.getId(), url.getName());
                } else {
                    log.error("Failed to get generated ID for URL: {}", url.getName());
                    throw new SQLException("DB have not returned an id after saving an entity");
                }
            }
        } catch (SQLException e) {
            log.error("Error saving URL: {}", url.getName(), e);
            throw e;
        }
    }

    public static Optional<Url> findByName(String name) throws SQLException {
        var sql = "SELECT * FROM urls WHERE name = ?";
        log.debug("Executing SQL: {} with parameter: {}", sql, name);

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                Url url = new Url();
                url.setId(rs.getLong("id"));
                url.setName(rs.getString("name"));
                url.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                log.debug("Found URL by name: {}", name);
                return Optional.of(url);
            }
            log.debug("URL not found by name: {}", name);
            return Optional.empty();
        }
    }

    public static Optional<Url> findById(long id) throws SQLException {
        var sql = "SELECT * FROM urls WHERE id = ?";
        log.debug("Executing SQL: {} with parameter: {}", sql, id);

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                Url url = new Url();
                url.setId(rs.getLong("id"));
                url.setName(rs.getString("name"));
                url.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                log.debug("Found URL by ID: {}", id);
                return Optional.of(url);
            }
            log.debug("URL not found by ID: {}", id);
            return Optional.empty();
        }
    }

    public static List<Url> getEntities() throws SQLException {
        var sql = "SELECT * FROM urls ORDER BY created_at DESC";
        log.info("Executing SQL: {}", sql);

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            var urls = new ArrayList<Url>();
            while (rs.next()) {
                Url url = new Url();
                url.setId(rs.getLong("id"));
                url.setName(rs.getString("name"));
                url.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                urls.add(url);
                log.debug("Retrieved URL: ID={}, Name={}", url.getId(), url.getName());
            }
            log.info("Total URLs retrieved: {}", urls.size());
            return urls;
        }
    }
}
