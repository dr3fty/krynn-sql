package dev.krynn.sql.connection;

import com.zaxxer.hikari.HikariConfig;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnection {

    Connection connection() throws SQLException;

    void close();

    void config(HikariConfig config);

}
