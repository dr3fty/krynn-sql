/*
 * Copyright (c) 2019 Oskarr1239
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
