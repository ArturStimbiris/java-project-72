package hexlet.code.controllers;

import hexlet.code.model.Url;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UrlController extends RootController {
    public static void save(Url url) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
        }
    }

    public static Url findByName(String name) throws SQLException {
        var sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                var url = new Url();
                url.setId(rs.getLong("id"));
                url.setName(rs.getString("name"));
                url.setCreatedAt(rs.getTimestamp("created_at"));
                return url;
            }
            return null;
        }
    }

    public static List<Url> getAll() throws SQLException {
        var sql = "SELECT * FROM urls ORDER BY created_at DESC";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            var urls = new ArrayList<Url>();

            while (rs.next()) {
                var url = new Url();
                url.setId(rs.getLong("id"));
                url.setName(rs.getString("name"));
                url.setCreatedAt(rs.getTimestamp("created_at"));
                urls.add(url);
            }
            return urls;
        }
    }
}
