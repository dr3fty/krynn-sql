package dev.krynn.sql;

import com.zaxxer.hikari.HikariConfig;
import dev.krynn.sql.compiler.Compiler;
import dev.krynn.sql.connection.DatabaseConnection;
import dev.krynn.sql.database.Database;
import dev.krynn.sql.database.DatabaseFactory;
import dev.krynn.sql.impl.compiler.CompilerImpl;
import dev.krynn.sql.impl.connection.DatabaseConnectionImpl;
import dev.krynn.sql.impl.database.DatabaseFactoryImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class KrynnSQL {

    private static Compiler compiler = new CompilerImpl();
    private static DatabaseConnection connection = new DatabaseConnectionImpl();
    private static DatabaseFactory databaseFactory;

    public static void initialize(HikariConfig config) {
        connection.config(config);
        databaseFactory = new DatabaseFactoryImpl(connection);
    }

    public static Database getDatabase(String name) {
        return databaseFactory.get(name);
    }

    public static Connection getConnection() throws SQLException {
        return connection.connection();
    }

    public static Compiler getCompiler() {
        return compiler;
    }

    public static DatabaseFactory getDatabaseFactory() {
        return databaseFactory;
    }

}
