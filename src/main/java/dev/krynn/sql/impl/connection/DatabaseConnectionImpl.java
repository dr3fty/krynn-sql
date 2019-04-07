package dev.krynn.sql.impl.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.krynn.sql.connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionImpl implements DatabaseConnection {

    private HikariDataSource dataSource;

    @Override
    public Connection connection() throws SQLException{
        return this.dataSource.getConnection();
    }

    @Override
    public void close() {
        this.dataSource.close();
    }

    @Override
    public void config(HikariConfig config) {
        this.dataSource = new HikariDataSource(config);
    }
}
