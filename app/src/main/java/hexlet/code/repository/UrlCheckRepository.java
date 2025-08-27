package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck urlCheck) throws SQLException {
        String sql = "INSERT INTO url_checks (url_id, status_code, title, h1, description, created_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, urlCheck.getUrlId());
            stmt.setInt(2, urlCheck.getStatusCode());
            stmt.setString(3, urlCheck.getTitle());
            stmt.setString(4, urlCheck.getH1());
            stmt.setString(5, urlCheck.getDescription());
            stmt.setObject(6, LocalDateTime.now());

            stmt.executeUpdate();

            try (var generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    urlCheck.setId(generatedKeys.getLong(1));
                    log.info("URL check saved. ID: {}, URL ID: {}", urlCheck.getId(), urlCheck.getUrlId());
                }
            }
        } catch (SQLException e) {
            log.error("Error saving URL check for URL ID: {}", urlCheck.getUrlId(), e);
            throw e;
        }
    }

    public static List<UrlCheck> findByUrlId(Long urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at DESC";
        log.debug("Executing SQL: {} with parameter: {}", sql, urlId);

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();
            var checks = new ArrayList<UrlCheck>();

            while (resultSet.next()) {
                var check = new UrlCheck();
                check.setId(resultSet.getLong("id"));
                check.setUrlId(resultSet.getLong("url_id"));
                check.setStatusCode(resultSet.getInt("status_code"));
                check.setTitle(resultSet.getString("title"));
                check.setH1(resultSet.getString("h1"));
                check.setDescription(resultSet.getString("description"));
                check.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                checks.add(check);
            }
            log.debug("Found {} checks for URL ID: {}", checks.size(), urlId);
            return checks;
        }
    }

    public static UrlCheck findLastCheckByUrlId(Long urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at DESC LIMIT 1";
        log.debug("Executing SQL: {} with parameter: {}", sql, urlId);

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                var check = new UrlCheck();
                check.setId(resultSet.getLong("id"));
                check.setUrlId(resultSet.getLong("url_id"));
                check.setStatusCode(resultSet.getInt("status_code"));
                check.setTitle(resultSet.getString("title"));
                check.setH1(resultSet.getString("h1"));
                check.setDescription(resultSet.getString("description"));
                check.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                log.debug("Found last check for URL ID: {}", urlId);
                return check;
            }
            log.debug("No checks found for URL ID: {}", urlId);
            return null;
        }
    }

    /**
     * Fetch the latest check for each URL in a single query.
     *
     * @return a Map where each key is a URL ID and the corresponding value
     *         is the most recent UrlCheck for that URL
     * @throws SQLException if a database access error occurs
     */
    public static Map<Long, UrlCheck> findLatestChecks() throws SQLException {
        String sql = "SELECT DISTINCT ON (url_id) * FROM url_checks "
                   + "ORDER BY url_id DESC, id DESC";
        log.debug("Executing SQL to fetch latest checks: {}", sql);

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            var resultSet = stmt.executeQuery();
            Map<Long, UrlCheck> result = new HashMap<>();

            while (resultSet.next()) {
                UrlCheck check = new UrlCheck();
                check.setId(resultSet.getLong("id"));
                check.setUrlId(resultSet.getLong("url_id"));
                check.setStatusCode(resultSet.getInt("status_code"));
                check.setTitle(resultSet.getString("title"));
                check.setH1(resultSet.getString("h1"));
                check.setDescription(resultSet.getString("description"));
                check.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                result.put(check.getUrlId(), check);
            }
            log.debug("Fetched {} latest checks", result.size());
            return result;
        }
    }
}
