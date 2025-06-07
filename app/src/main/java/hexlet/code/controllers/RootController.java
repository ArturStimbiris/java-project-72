package hexlet.code.controllers;

import com.zaxxer.hikari.HikariDataSource;

public class RootController {
    protected static HikariDataSource dataSource;

    public static void setDataSource(HikariDataSource newDataSource) {
        dataSource = newDataSource;
    }
}
