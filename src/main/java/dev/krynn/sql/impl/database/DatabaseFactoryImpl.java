package dev.krynn.sql.impl.database;

import dev.krynn.sql.connection.DatabaseConnection;
import dev.krynn.sql.database.Database;
import dev.krynn.sql.database.DatabaseFactory;
import dev.krynn.sql.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseFactoryImpl implements DatabaseFactory {

    private DatabaseConnection connection;

    public DatabaseFactoryImpl(DatabaseConnection connection) {
        this.connection = connection;
    }

    @Override
    public Database get(String name) {
        return new DatabaseImpl(name);
    }

    @Override
    public Database getOrCreate(String name) {
        //TODO soon
        throw new UnsupportedOperationException();
    }

    @Override
    public void create(String name) {
        try(Connection con = connection.connection()) {
            PreparedStatement statement = con.prepareStatement(Query.CREATE_DATABASE);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
