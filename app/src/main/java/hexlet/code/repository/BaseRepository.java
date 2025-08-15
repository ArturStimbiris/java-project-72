package hexlet.code.repository;

import com.zaxxer.hikari.HikariDataSource;

public class BaseRepository {
    protected static HikariDataSource dataSource;

    public static void setDataSource(HikariDataSource newDataSource) {
        dataSource = newDataSource;
    }
}
